package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import org.springframework.http.ResponseEntity;

public interface UndockScooterService {
    ResponseEntity<BasicResponse> undockScooter(Long scooterId);
}
