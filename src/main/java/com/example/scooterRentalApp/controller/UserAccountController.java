package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.api.request.CreateUserAccountRequest;
import com.example.scooterRentalApp.api.response.CreateUserAccountResponse;
import com.example.scooterRentalApp.service.*;
import com.example.scooterRentalApp.service.impl.UserAccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-account")
public class UserAccountController {

    private UserAccountService userAccountService;


    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<CreateUserAccountResponse> createUserAccount(
            @RequestBody CreateUserAccountRequest request
    ) {
        return userAccountService.createUserAccount(request);
    }

    @PutMapping(value = "/{accountId}/recharge", produces = "application/json")
    public ResponseEntity<BasicResponse> rechargeUserAccount(
            @PathVariable Long accountId,
            @RequestParam String amount
    ) {
        return userAccountService.rechargeUserAccount(accountId, amount);
    }

    @GetMapping(value="/{userId}/balance", produces = "application/json")
    public ResponseEntity<BasicResponse> displayBalance(
            @PathVariable Long userId
    ){
        return userAccountService.displayBalance(userId);
    }

    @GetMapping(value="/scooter", produces = "application/json")
    public ResponseEntity<BasicResponse> displayScooter(
            @RequestParam String userEmail
    ){
        return userAccountService.displayRentedScooter(userEmail);
    }

    @DeleteMapping(value = "/remove", produces = "application/json")
    public ResponseEntity<BasicResponse> removeUser(
            @RequestParam String userEmail
    ) {
        return userAccountService.removeAccount(userEmail);
    }

    @PutMapping(value = "/{userId}/update", produces = "application/json")
    public ResponseEntity<BasicResponse> updateEmail(
            @PathVariable Long userId,
            @RequestParam String userEmail
    ) {
        return userAccountService.updateEmail(userId, userEmail);
    }
}
