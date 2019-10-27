package com.example.scooterRentalApp.api.response;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.model.Scooter;

public class DisplayRentedScooterResponse extends BasicResponse {

    public DisplayRentedScooterResponse() {
    }

    public DisplayRentedScooterResponse(String responseMsg, Scooter scooter) {
        super(responseMsg + " " + scooter.toString());
    }
}
