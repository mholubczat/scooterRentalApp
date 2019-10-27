package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.repository.ScooterRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.UndockScooterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UndockScooterServiceImpl extends AbstractCommonService implements UndockScooterService {
    private ScooterRepository scooterRepository;

    public UndockScooterServiceImpl(MsgSource msgSource, ScooterRepository scooterRepository) {
        super(msgSource);
        this.scooterRepository = scooterRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> undockScooter(Long scooterId) {
        Scooter scooter = extractScooterFromRepository(scooterId);
        checkScooterIsRented(scooter);
        scooter.setScooterDock(null);
        scooterRepository.save(scooter);
        return ResponseEntity.ok(BasicResponse.of(msgSource.OK010));
    }

    private Scooter extractScooterFromRepository(Long scooterId) {
        Optional<Scooter> optionalScooter = scooterRepository.findById(scooterId);
        if (!optionalScooter.isPresent()) {
            throw new CommonConflictException(msgSource.ERR010);
        }
        return optionalScooter.get();
    }

    private void checkScooterIsRented(Scooter scooter) {
        if (scooter.getUserAccount() != null) {
            throw new CommonConflictException(msgSource.ERR015);
        }
    }
}
