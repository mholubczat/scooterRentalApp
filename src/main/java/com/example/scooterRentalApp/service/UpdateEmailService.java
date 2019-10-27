package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import org.springframework.http.ResponseEntity;

public interface UpdateEmailService {
    ResponseEntity<BasicResponse> updateEmail(Long userId, String userEmail);
}
