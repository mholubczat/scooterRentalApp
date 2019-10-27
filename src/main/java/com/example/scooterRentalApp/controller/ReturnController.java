package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.service.RentalService;
import com.example.scooterRentalApp.service.ReturnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("return")
public class ReturnController {

    private ReturnService returnService;

    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @PutMapping(value = "/{dockId}/user", produces = "application/json")
    public ResponseEntity<BasicResponse> returnScooter(
            @PathVariable Long dockId,
            @RequestParam Long accountId
    ) {
        return returnService.returnScooter(dockId, accountId);
    }

}
