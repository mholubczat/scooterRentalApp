package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import org.springframework.http.ResponseEntity;

public interface ReturnService {
    ResponseEntity<BasicResponse> returnScooter(Long dockId, Long accountId);
}
