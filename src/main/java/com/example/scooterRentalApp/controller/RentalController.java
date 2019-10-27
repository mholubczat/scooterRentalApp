package com.example.scooterRentalApp.controller;

        import com.example.scooterRentalApp.api.BasicResponse;
        import com.example.scooterRentalApp.service.RentalService;
        import com.example.scooterRentalApp.service.ReturnService;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rental")
public class RentalController {

    private RentalService rentalService;
    private ReturnService returnService;

    public RentalController(RentalService rentalService, ReturnService returnService) {
        this.rentalService = rentalService;
        this.returnService = returnService;
    }

    @PutMapping(value = "/{scooterId}/scooter", produces = "application/json")
    public ResponseEntity<BasicResponse> rentScooter(
            @PathVariable Long scooterId,
            @RequestParam Long accountId
    ) {
        return rentalService.rentScooter(scooterId, accountId);
    }

    @PutMapping(value = "/{dockId}/user", produces = "application/json")
    public ResponseEntity<BasicResponse> returnScooter(
            @PathVariable Long dockId,
            @RequestParam Long accountId
    ) {
        return returnService.returnScooter(dockId, accountId);
    }

}
