package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.service.DisplayRentedScooter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("display")
public class DisplayScooterController {
    private DisplayRentedScooter displayRentedScooter;

    public DisplayScooterController(DisplayRentedScooter displayRentedScooter) {
        this.displayRentedScooter = displayRentedScooter;
    }

    @GetMapping(value="/{userId}", produces = "application/json")
    public ResponseEntity<BasicResponse> displayScooter(
            @PathVariable Long userId
    ){
        return displayRentedScooter.displayRentedScooter(userId);
    }

}


