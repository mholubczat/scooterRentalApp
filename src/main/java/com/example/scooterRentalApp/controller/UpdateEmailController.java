package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.service.UpdateEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("update")
public class UpdateEmailController{
    private UpdateEmailService updateEmailService;

    public UpdateEmailController(UpdateEmailService updateEmailService) {
        this.updateEmailService = updateEmailService;
    }

    @PutMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<BasicResponse> updateEmail(
            @PathVariable Long userId,
            @RequestParam String userEmail
    ) {
        return updateEmailService.updateEmail(userId, userEmail);
    }
}
