package com.example.scooterRentalApp.api.request;

public class CreateDockRequest {
    private String dockName;
    private Integer availablePlace;

    public String getDockName() {
        return dockName;
    }

    public void setDockName(String dockName) {
        this.dockName = dockName;
    }

    public Integer getAvailablePlace() {
        return availablePlace;
    }

    public void setAvailablePlace(Integer availablePlace) {
        this.availablePlace = availablePlace;
    }
}
