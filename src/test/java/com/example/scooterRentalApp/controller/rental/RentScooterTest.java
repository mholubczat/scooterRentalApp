package com.example.scooterRentalApp.controller.rental;

import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.ScooterRepository;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.validation.constraints.AssertTrue;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RentScooterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private ScooterRepository scooterRepository;

    @Test
    public void rentalSuccessful() throws Exception {
        BigDecimal initBalance = userAccountRepository.findById(19L).get().getBalance();
        mockMvc
                .perform(put("/rental/{scooterId}/rent", 5).param("accountId", "19"))
                .andExpect(status().isOk());
        userAccountRepository.findById(19L).ifPresent(userAccount -> Assert.assertEquals(5L, (long) userAccount.getScooter().getId()));
        userAccountRepository.findById(19L).ifPresent(userAccount -> Assert.assertNotEquals(userAccount.getBalance(), initBalance));
    }

    @Test
    public void scooterNotAvailableToRent() throws Exception {
        mockMvc
                .perform(put("/scooter/{scooterId}/undock", 6));
        mockMvc
                .perform(put("/rental/{scooterId}/rent", 6).param("accountId", "17"))
                .andExpect(status().is(409))
                .andExpect(content().json("{\n" +
                "    \"errorCode\": \"ERR011\",\n" +
                "    \"errorMsg\": \"Hulajnoga o podanym id nie jest dostępna do wypożyczenia.\",\n" +
                "    \"status\": \"ERROR\"\n" +
                "}"));
    }

    @Test
    public void userAlreadyRents() throws Exception {
        mockMvc
                .perform(put("/rental/{scooterId}/rent", 7).param("accountId", "16"));
        mockMvc
                .perform(put("/rental/{scooterId}/rent", 8).param("accountId", "16"))
                .andExpect(status().is(409))
                .andExpect(content().json("{\"errorCode\":\"ERR012\",\"errorMsg\":\"Konto o podanym id posiada już wypożyczoną hulajnogę.\",\"status\":\"ERROR\"}"));
    }

    @Test
    public void insufficientFundsToRent() throws Exception {

        mockMvc
                .perform(put("/rental/{scooterId}/rent", 10).param("accountId", "18"))
                .andExpect(status().is(409))
                .andExpect(content().json("{\"errorCode\":\"ERR013\",\"errorMsg\":\"Konto o podanym id nie posiada wystarczających środków na koncie.\",\"status\":\"ERROR\"}"));
    }
}
