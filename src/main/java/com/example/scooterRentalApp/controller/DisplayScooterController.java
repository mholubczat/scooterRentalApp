package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.service.DisplayRentedScooter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("display")
public class DisplayScooterController {
    private DisplayRentedScooter displayRentedScooter;

    public DisplayScooterController(DisplayRentedScooter displayRentedScooter) {
        this.displayRentedScooter = displayRentedScooter;
    }

    @GetMapping(value="", produces = "application/json")
    public ResponseEntity<BasicResponse> displayScooter(
            @RequestParam String userEmail
    ){
        return displayRentedScooter.displayRentedScooter(userEmail);
    }

}


