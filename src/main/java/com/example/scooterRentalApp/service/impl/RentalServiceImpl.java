package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.model.ScooterDock;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.ScooterDockRepository;
import com.example.scooterRentalApp.repository.ScooterRepository;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class RentalServiceImpl extends AbstractCommonService implements RentalService {

    private UserAccountRepository userAccountRepository;
    private ScooterRepository scooterRepository;
    private ScooterDockRepository scooterDockRepository;

    public RentalServiceImpl(MsgSource msgSource, UserAccountRepository userAccountRepository, ScooterRepository scooterRepository, ScooterDockRepository scooterDockRepository) {
        super(msgSource);
        this.userAccountRepository = userAccountRepository;
        this.scooterRepository = scooterRepository;
        this.scooterDockRepository = scooterDockRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> rentScooter(Long scooterId, Long accountId) {
        UserAccount userAccount = extractUserAccountFromRepository(accountId);
        Scooter scooter = extractScooterFromRepository(scooterId);
        checkScooterIsAvailableToRent(scooter);
        checkUserAccountAlreadyHaveAnyRental(userAccount);
        checkUserAccountHaveEnoughMoney(userAccount, scooter.getRentalPrice());
        finalizeScooterRental(userAccount, scooter);

        return ResponseEntity.ok(BasicResponse.of(msgSource.OK004));
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

    private UserAccount extractUserAccountFromRepository(Long accountId) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(accountId);
        if (!optionalUserAccount.isPresent()) {
            throw new CommonConflictException(msgSource.ERR006);
        }
        return optionalUserAccount.get();
    }

    private Scooter extractScooterFromRepository(Long scooterId) {
        Optional<Scooter> optionalScooter = scooterRepository.findById(scooterId);
        if (!optionalScooter.isPresent()) {
            throw new CommonConflictException(msgSource.ERR010);
        }
        return optionalScooter.get();
    }

    private void checkScooterIsAvailableToRent(Scooter scooter) {
        if (scooter.getScooterDock() == null || scooter.getUserAccount() != null) {
            throw new CommonConflictException(msgSource.ERR011);
        }
    }

    private void checkUserAccountAlreadyHaveAnyRental(UserAccount userAccount) {
        if (userAccount.getScooter() != null) {
            throw new CommonConflictException(msgSource.ERR012);
        }
    }

    private void checkUserAccountHaveEnoughMoney(UserAccount userAccount, BigDecimal rentalPrice) {
        if (userAccount.getBalance().compareTo(rentalPrice) < 0) {
            throw new CommonConflictException(msgSource.ERR013);
        }
    }

    private void finalizeScooterRental(UserAccount userAccount, Scooter scooter) {
        userAccount.setBalance(userAccount.getBalance().subtract(scooter.getRentalPrice()));
        scooter.setScooterDock(null);
        scooter.setUserAccount(userAccount);
        scooterRepository.save(scooter);
    }
}

