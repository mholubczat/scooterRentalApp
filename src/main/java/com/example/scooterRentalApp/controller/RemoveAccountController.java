package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.service.RemoveAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("remove")
public class RemoveAccountController {

        private RemoveAccountService removeAccountService;

    public RemoveAccountController(RemoveAccountService removeAccountService) {
        this.removeAccountService = removeAccountService;
    }

    @DeleteMapping(value = "/user", produces = "application/json")
        public ResponseEntity<BasicResponse> removeUser(
                @RequestParam String userEmail
        ) {
            return removeAccountService.removeAccount(userEmail);
        }

    }
