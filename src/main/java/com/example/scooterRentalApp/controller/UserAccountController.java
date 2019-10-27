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
    private DisplayBalanceService displayBalanceService;
    private DisplayRentedScooterService displayRentedScooterService;
    private RemoveAccountService removeAccountService;
    private UpdateEmailService updateEmailService;

    public UserAccountController(UserAccountService userAccountService, DisplayBalanceService displayBalanceService, DisplayRentedScooterService displayRentedScooterService, RemoveAccountService removeAccountService, UpdateEmailService updateEmailService) {
        this.userAccountService = userAccountService;
        this.displayBalanceService = displayBalanceService;
        this.displayRentedScooterService = displayRentedScooterService;
        this.removeAccountService = removeAccountService;
        this.updateEmailService = updateEmailService;
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
        return displayBalanceService.displayBalance(userId);
    }

    @GetMapping(value="/scooter", produces = "application/json")
    public ResponseEntity<BasicResponse> displayScooter(
            @RequestParam String userEmail
    ){
        return displayRentedScooterService.displayRentedScooter(userEmail);
    }

    @DeleteMapping(value = "/remove", produces = "application/json")
    public ResponseEntity<BasicResponse> removeUser(
            @RequestParam String userEmail
    ) {
        return removeAccountService.removeAccount(userEmail);
    }

    @PutMapping(value = "/{userId}/update", produces = "application/json")
    public ResponseEntity<BasicResponse> updateEmail(
            @PathVariable Long userId,
            @RequestParam String userEmail
    ) {
        return updateEmailService.updateEmail(userId, userEmail);
    }
}
