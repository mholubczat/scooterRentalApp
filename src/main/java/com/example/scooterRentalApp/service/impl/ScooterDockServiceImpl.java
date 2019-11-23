package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.request.CreateDockRequest;
import com.example.scooterRentalApp.api.response.CreateDockResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonBadRequestException;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.model.ScooterDock;
import com.example.scooterRentalApp.repository.ScooterDockRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.ScooterDockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.scooterRentalApp.common.ValidationUtils.isNull;
import static com.example.scooterRentalApp.common.ValidationUtils.isNullOrEmpty;

@Service
public class ScooterDockServiceImpl extends AbstractCommonService implements ScooterDockService {

    private ScooterDockRepository scooterDockRepository;

    public ScooterDockServiceImpl(MsgSource msgSource, ScooterDockRepository scooterDockRepository) {
        super(msgSource);
        this.scooterDockRepository = scooterDockRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<Set<Scooter>> getAllDockScooters(Long scooterDockId) {
        Optional<ScooterDock> optionalScooterDock = scooterDockRepository.findById(scooterDockId);
        if (!optionalScooterDock.isPresent()) {
            throw new CommonConflictException(msgSource.ERR008);
        }
        return ResponseEntity.ok(optionalScooterDock.get().getScooters());
    }

    @Override
    @Transactional
    public ResponseEntity<CreateDockResponse> createScooterDock(CreateDockRequest request) {
            validateAddScooterDockRequest(request);
            checkDockNameExists(request.getDockName());
            ScooterDock addedScooterDock = addScooterDockToDataSource(request);
            return ResponseEntity.ok(new CreateDockResponse(msgSource.OK011, addedScooterDock.getId()));
        }

    private ScooterDock addScooterDockToDataSource(CreateDockRequest request) {
        ScooterDock scooterDock = new ScooterDock(
                null,
                request.getAvailablePlace(),
                request.getDockName(),
                new HashSet<>()
        );
        return scooterDockRepository.save(scooterDock);
    }

    private void checkDockNameExists(String dockName) {
        if (!scooterDockRepository.findByDockName(dockName).isEmpty())
        {
            throw new CommonBadRequestException(msgSource.ERR016);
        }
    }

    private void validateAddScooterDockRequest(CreateDockRequest request) {
        if (isNullOrEmpty(request.getDockName())
                || isNull(request.getAvailablePlace())
                ) {
            throw new CommonBadRequestException(msgSource.ERR001);
        }
    }


}


