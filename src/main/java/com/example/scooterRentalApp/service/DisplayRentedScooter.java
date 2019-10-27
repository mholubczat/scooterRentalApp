package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import org.springframework.http.ResponseEntity;

public interface DisplayRentedScooter {
    ResponseEntity<BasicResponse> displayRentedScooter(Long accountId);
}
