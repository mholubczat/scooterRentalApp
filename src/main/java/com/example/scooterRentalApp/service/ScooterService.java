package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.api.request.AddScooterRequest;
import com.example.scooterRentalApp.api.response.AddScooterResponse;
import org.springframework.http.ResponseEntity;

public interface ScooterService {

    ResponseEntity<AddScooterResponse> addScooter(AddScooterRequest request);
    ResponseEntity<BasicResponse> undockScooter(Long scooterId);
}


