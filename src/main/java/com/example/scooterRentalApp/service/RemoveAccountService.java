package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import org.springframework.http.ResponseEntity;

public interface RemoveAccountService {
    ResponseEntity<BasicResponse> removeAccount(String userEmail);
}
