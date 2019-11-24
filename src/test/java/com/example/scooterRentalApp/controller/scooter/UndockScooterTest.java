package com.example.scooterRentalApp.controller.scooter;

import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.repository.ScooterRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UndockScooterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScooterRepository scooterRepository;

    @Test
    public void undockSuccessful() throws Exception {
        mockMvc
                .perform(put("/scooter/{scooterId}/undock", 7))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"responseMsg\": \"Poprawnie usunięto hulajnogę ze stacji.\",\n" +
                        "    \"status\": \"SUCCESS\"\n" +
                        "}"));
        scooterRepository.findById(7L).ifPresent(scooter -> Assert.assertNull(scooter.getScooterDock()));
    }

    @Test
    public void undockNotExistingScooter() throws Exception {
        mockMvc
                .perform(put("/scooter/{scooterId}/undock", 666))
                .andExpect(status().is(409))
                .andExpect(content().json(
                        "{\n" +
                                "    \"errorCode\": \"ERR010\",\n" +
                                "    \"errorMsg\": \"Hulajnoga o podanym id nie istnieje.\",\n" +
                                "    \"status\": \"ERROR\"\n" +
                                "}"
                ));
    }
    @Test
    public void undockRentScooter() throws Exception {
        mockMvc
                .perform(put("/rental/{scooterId}/rent",6).param("accountId","16"));
        mockMvc
                .perform(put("/scooter/{scooterId}/undock", 6))
                .andExpect(status().is(409))
                .andExpect(content().json(
                        "{\"errorCode\":\"ERR015\",\"errorMsg\":\"Ta hulajnoga nie jest zadokowana.\",\"status\":\"ERROR\"}"
                ))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void undockUndockedScooter() throws Exception {
        mockMvc
                .perform(put("/scooter/{scooterId}/undock", 9));
        mockMvc
                .perform(put("/scooter/{scooterId}/undock", 9))
                .andExpect(status().is(409))
                .andExpect(content().json(
                        "{\"errorCode\":\"ERR015\",\"errorMsg\":\"Ta hulajnoga nie jest zadokowana.\",\"status\":\"ERROR\"}"
                ));
    }
}