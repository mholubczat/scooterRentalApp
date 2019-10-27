package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import org.springframework.http.ResponseEntity;

public interface DisplayRentedScooterService {
    ResponseEntity<BasicResponse> displayRentedScooter(String userEmail);
}
