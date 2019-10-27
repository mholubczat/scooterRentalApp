package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.ScooterDock;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.ScooterDockRepository;
import com.example.scooterRentalApp.repository.ScooterRepository;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.ReturnService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class ReturnServiceImpl extends AbstractCommonService implements ReturnService {

    private UserAccountRepository userAccountRepository;
    private ScooterDockRepository scooterDockRepository;

    public ReturnServiceImpl(MsgSource msgSource, UserAccountRepository userAccountRepository, ScooterDockRepository scooterDockRepository, ScooterRepository scooterRepository) {
        super(msgSource);
        this.userAccountRepository = userAccountRepository;
        this.scooterDockRepository = scooterDockRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> returnScooter(Long dockId, Long accountId) {

        UserAccount userAccount = extractUserAccountFromRepository(accountId);
        ScooterDock scooterDock = extractScooterDockFromRepository(dockId);
        checkDockIsAvailable(scooterDock);
        checkUserRentsAScooter(userAccount);
        finalizeScooterReturn(userAccount, scooterDock);

        return ResponseEntity.ok(BasicResponse.of(msgSource.OK005));
    }



    private UserAccount extractUserAccountFromRepository(Long accountId) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(accountId);
        if (!optionalUserAccount.isPresent()) {
            throw new CommonConflictException(msgSource.ERR006);
        }
        return optionalUserAccount.get();
    }

    private ScooterDock extractScooterDockFromRepository(Long dockId) {
        Optional<ScooterDock> optionalScooterDock = scooterDockRepository.findById(dockId);
        if (!optionalScooterDock.isPresent()) {
            throw new CommonConflictException(msgSource.ERR008);
        }
        return optionalScooterDock.get();
    }

    private void checkDockIsAvailable(ScooterDock scooterDock){
        if (scooterDock.getScooters().size() == scooterDock.getAvailablePlace()) {
            throw new CommonConflictException(msgSource.ERR009);
        }
    }

    private void checkUserRentsAScooter(UserAccount userAccount) {
        if (userAccount.getScooter() == null) {
            throw new CommonConflictException(msgSource.ERR014);
        }
    }

    private void finalizeScooterReturn(UserAccount userAccount, ScooterDock scooterDock) {
        userAccount.getScooter().setScooterDock(scooterDock);
        userAccount.getScooter().setUserAccount(null);
        userAccountRepository.save(userAccount);
    }
}