package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.request.CreateDockRequest;
import com.example.scooterRentalApp.api.response.CreateDockResponse;
import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.service.ScooterDockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("scooter-dock")
public class ScooterDockController {

    private ScooterDockService scooterDockService;

    public ScooterDockController(ScooterDockService scooterDockService) {
        this.scooterDockService = scooterDockService;
    }

    @GetMapping(value = "/{scooterDockId}/scooters", produces = "application/json")
    public ResponseEntity<Set<Scooter>> rechargeUserAccount(
            @PathVariable Long scooterDockId
    ) {
        return scooterDockService.getAllDockScooters(scooterDockId);
    }

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<CreateDockResponse> addDockService(
        @RequestBody CreateDockRequest request
    ){
        return scooterDockService.createScooterDock(request);
    }

}
