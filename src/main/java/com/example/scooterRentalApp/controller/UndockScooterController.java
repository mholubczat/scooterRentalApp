package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.service.UndockScooterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("undock")
public class UndockScooterController {
    private UndockScooterService undockScooterService;

    public UndockScooterController(UndockScooterService undockScooterService) {
        this.undockScooterService = undockScooterService;
    }

    @PutMapping(value = "/{scooterId}", produces = "application/json")
    public ResponseEntity<BasicResponse> undockScooter(
            @PathVariable Long scooterId
    ) {
        return undockScooterService.undockScooter(scooterId);
    }
}
