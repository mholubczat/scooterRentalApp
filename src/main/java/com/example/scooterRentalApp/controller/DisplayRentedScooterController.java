package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.service.DisplayRentedScooterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("display")
public class DisplayRentedScooterController {
    private DisplayRentedScooterService displayRentedScooterService;

    public DisplayRentedScooterController(DisplayRentedScooterService displayRentedScooterService) {
        this.displayRentedScooterService = displayRentedScooterService;
    }

    @GetMapping(value="/scooter", produces = "application/json")
    public ResponseEntity<BasicResponse> displayScooter(
            @RequestParam String userEmail
    ){
        return displayRentedScooterService.displayRentedScooter(userEmail);
    }

}


