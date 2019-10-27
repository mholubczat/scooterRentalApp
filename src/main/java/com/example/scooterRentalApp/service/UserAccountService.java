package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.api.request.CreateUserAccountRequest;
import com.example.scooterRentalApp.api.response.CreateUserAccountResponse;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface UserAccountService {

    ResponseEntity<CreateUserAccountResponse> createUserAccount(CreateUserAccountRequest request);
    ResponseEntity<BasicResponse> rechargeUserAccount(Long userId, String amount);
    ResponseEntity<BasicResponse> updateEmail(Long userId, String userEmail);
    ResponseEntity<BasicResponse> removeAccount(String userEmail);
    ResponseEntity<BasicResponse> displayRentedScooter(String userEmail);
    ResponseEntity<BasicResponse> displayBalance(Long userId);
}
