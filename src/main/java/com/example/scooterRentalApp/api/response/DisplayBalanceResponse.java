package com.example.scooterRentalApp.api.response;

import com.example.scooterRentalApp.api.BasicResponse;

import java.math.BigDecimal;

public class DisplayBalanceResponse extends BasicResponse {

    public DisplayBalanceResponse() {
    }

    public DisplayBalanceResponse(String responseMsg, BigDecimal balance) {
        super(responseMsg+ " " +balance);
    }
}
