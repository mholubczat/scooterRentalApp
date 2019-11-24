package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.response.CreateUserAccountResponse;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    void ifCreateAccountRequestContainsWrongEmailAddressShouldReturnHttpCode400AndErrorMsg() throws Exception {
        mockMvc
                .perform(
                        post("/user-account/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n" +
                                        "\t\"ownerUsername\": \"janek222\",\n" +
                                        "\t\"ownerEmail\": \"janek222example.com\",\n" +
                                        "\t\"ownerAge\": 35\n" +
                                        "}"
                                )
                )
                .andExpect(status().is(400))
                .andExpect(content().json(
                        "{\n" +
                        "\t\"errorCode\": \"ERR002\",\n" +
                        "\t\"errorMsg\": \"Podaj poprawny adres e-mail.\",\n" +
                        "\t\"status\": \"ERROR\"\n" +
                        "}"
                    )
                )
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void ifCreateAccountRequestContainsWrongAgeShouldReturnHttpCode409AndErrorMsg() throws Exception {
        mockMvc
                .perform(
                        post("/user-account/create")
                                .content(
                                        "{\n" +
                                        "\t\"ownerUsername\": \"janek222\",\n" +
                                        "\t\"ownerEmail\": \"janek222@example.com\",\n" +
                                        "\t\"ownerAge\": 123\n" +
                                        "}"
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(409))
                .andExpect(content().json(
                        "{\n" +
                        "\t\"errorCode\": \"ERR003\",\n" +
                        "\t\"errorMsg\": \"Wiek powinien mieścić się w zakresie od 1 do 100.\",\n" +
                        "\t\"status\": \"ERROR\"\n" +
                        "}"
                    )
                );
    }

    @Test
    void ifCreateAccountRequestContainsCorrectDataShouldReturnHttpCode200AndCreateAccount() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(
                        post("/user-account/create")
                                .content(
                                        "{\n" +
                                        "\t\"ownerUsername\": \"janek222\",\n" +
                                        "\t\"ownerEmail\": \"janek222@example.com\",\n" +
                                        "\t\"ownerAge\": 35\n" +
                                        "}"
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200))
                .andExpect(content().string(Matchers.containsString("Poprawnie utworzono konto użytkownika.")))
                .andExpect(content().string(Matchers.containsString("accountId")))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        CreateUserAccountResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CreateUserAccountResponse.class
        );
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(response.getAccountId());
        assertTrue(optionalUserAccount.isPresent());
    }

    @Test
    void ifCreateAccountRequestContainsAlreadyRegisterEmailShouldReturnHttpCode409AndError() throws Exception {
        mockMvc
                .perform(
                        post("/user-account/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n" +
                                        "\t\"ownerUsername\": \"janek222\",\n" +
                                        "\t\"ownerEmail\": \"janek222@example.com\",\n" +
                                        "\t\"ownerAge\": 35\n" +
                                        "}"
                                )
                );
        mockMvc
                .perform(
                        post("/user-account/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\n" +
                                        "\t\"ownerUsername\": \"janek222\",\n" +
                                        "\t\"ownerEmail\": \"janek222@example.com\",\n" +
                                        "\t\"ownerAge\": 35\n" +
                                        "}"
                                )
                )
                .andExpect(status().is(409))
                .andExpect(content().json(
                        "{\n" +
                        "\t\"errorCode\": \"ERR004\",\n" +
                        "\t\"errorMsg\": \"Konto o podanym adresie e-mail już istnieje.\",\n" +
                        "\t\"status\": \"ERROR\"\n" +
                        "}"
                ));
    }
}