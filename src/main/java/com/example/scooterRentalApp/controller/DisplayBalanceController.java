package com.example.scooterRentalApp.controller;


import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.service.DisplayBalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("display")
public class DisplayBalanceController {
    private DisplayBalanceService displayBalanceService;

    public DisplayBalanceController(DisplayBalanceService displayBalanceService) {
        this.displayBalanceService = displayBalanceService;
    }

    @GetMapping(value="/{userId}/balance", produces = "application/json")
    public ResponseEntity<BasicResponse> displayBalance(
            @PathVariable Long userId
    ){
        return displayBalanceService.displayBalance(userId);
    }

}


