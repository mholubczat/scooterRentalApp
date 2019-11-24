package com.example.scooterRentalApp.controller.scooter;

import com.example.scooterRentalApp.api.response.AddScooterResponse;
import com.example.scooterRentalApp.api.response.CreateUserAccountResponse;
import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.ScooterRepository;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddScooterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScooterRepository scooterRepository;

    @Test
    public void ifAddScooterIsCorrect() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(
                        post("/scooter/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n" +
                                                "\t\"modelName\": \"Nimbus2000\",\n" +
                                                "\t\"maxSpeed\": 40,\n" +
                                                "\t\"rentalPrice\": 35,\n" +
                                                "\t\"scooterDockId\": 2\n" +
                                                "}"
                                )
                )
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Poprawnie dodano hulajnogę do systemu.")))
                .andExpect(content().string(Matchers.containsString("scooterId")))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        AddScooterResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                AddScooterResponse.class
        );
        Optional<Scooter> optionalScooter = scooterRepository.findById(response.getScooterId());
        Assert.assertTrue(optionalScooter.isPresent());
    }

    @Test
    public void ifAddRequestIncomplete() throws Exception {
        mockMvc
                .perform(
                        post("/scooter/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n" +
                                                "\t\"modelName\": \"Nimbus2000\",\n" +
                                                "\t\"maxSpeed\": 40,\n" +
                                                "\t\"rentalPrice\": 35\n" +
                                                "}"
                                )
                )
                .andExpect(status().is(400))
                .andExpect(content().json(
                        "{\n" +
                                "\t\"errorCode\": \"ERR001\",\n" +
                                "\t\"errorMsg\": \"Dane wymagane do realizacji żądania są niekompletne.\",\n" +
                                "\t\"status\": \"ERROR\"\n" +
                                "}"
                ));
    }

    @Test
    public void ifAddRequestBadJson() throws Exception {
        mockMvc
                .perform(
                        post("/scooter/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n" +
                                                "\t\"modelName\": ,\n" +
                                                "\t\"maxSpeed\": 41,\n" +
                                                "\t\"rentalPrice\": 35,\n" +
                                                "\t\"scooterDockId\": 2\n" +
                                                "}"
                                )
                )
                .andExpect(status().is(400))
                .andDo(MockMvcResultHandlers.print())
               // .andExpect(content().string(Matchers.containsString("JSON parse error")))
             // .andExpect(content().string(Matchers.containsString("Bad Request")
                ;

    }


    @Test
    public void ifAddScooterMaxSpeedExceeded() throws Exception {
        mockMvc
                .perform(
                        post("/scooter/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n" +
                                                "\t\"modelName\": \"Nimbus2000\",\n" +
                                                "\t\"maxSpeed\": 41,\n" +
                                                "\t\"rentalPrice\": 35,\n" +
                                                "\t\"scooterDockId\": 2\n" +
                                                "}"
                                )
                )
                .andExpect(status().is(409))
                .andExpect(content().json(
                        "{\n" +
                                "    \"errorCode\": \"ERR007\",\n" +
                                "    \"errorMsg\": \"Maksymalna prędkość hulajnogi powinna mieścić się w zakresie od 1 do 40.\",\n" +
                                "    \"status\": \"ERROR\"\n" +
                                "}"
                ));
    }

    @Test
    public void ifAddScooterToInvalidDock() throws Exception {
        mockMvc
                .perform(
                        post("/scooter/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n" +
                                                "\t\"modelName\": \"Nimbus2000\",\n" +
                                                "\t\"maxSpeed\": 40,\n" +
                                                "\t\"rentalPrice\": 35,\n" +
                                                "\t\"scooterDockId\": 7\n" +
                                                "}"
                                )
                )
                .andExpect(status().is(409))
                .andExpect(content().json(
                        "{\n" +
                                "    \"errorCode\": \"ERR008\",\n" +
                                "    \"errorMsg\": \"Dok o podanym id nie istnieje.\",\n" +
                                "    \"status\": \"ERROR\"\n" +
                                "}"
                ));
    }
    @Test
    public void ifAddScooterToFullDock() throws Exception {
        mockMvc
                .perform(
                        post("/scooter/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n" +
                                                "\t\"modelName\": \"Nimbus2000\",\n" +
                                                "\t\"maxSpeed\": 40,\n" +
                                                "\t\"rentalPrice\": 35,\n" +
                                                "\t\"scooterDockId\": 3\n" +
                                                "}"
                                )
                )
                .andExpect(status().is(409))
                .andExpect(content().json(
                        "{\n" +
                                "    \"errorCode\": \"ERR009\",\n" +
                                "    \"errorMsg\": \"Dok jest pełny.\",\n" +
                                "    \"status\": \"ERROR\"\n" +
                                "}"
                ));
    }
}