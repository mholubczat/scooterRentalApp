package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonBadRequestException;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.DisplayRentedScooter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DisplayRentedScooterImpl extends AbstractCommonService implements DisplayRentedScooter {

    private UserAccountRepository userAccountRepository;

    public DisplayRentedScooterImpl(MsgSource msgSource, UserAccountRepository userAccountRepository) {
        super(msgSource);
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> displayRentedScooter(String userEmail) {
        UserAccount userAccount = extractUserAccountFromRepository(userEmail);
        if(userAccount.getScooter() == null){
            throw new CommonBadRequestException(msgSource.ERR014);
        }
        return ResponseEntity.ok(BasicResponse.of(userAccount.getScooter().toString()));
    }

    private UserAccount extractUserAccountFromRepository(String userEmail) {
        List<UserAccount> userAccounts = userAccountRepository.findByOwnerEmail(userEmail);
        if (userAccounts.isEmpty()) {
            throw new CommonConflictException(msgSource.ERR006);
        }
        return userAccounts.get(0);
    }

}

