package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.api.response.DisplayRentedScooterResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonBadRequestException;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.DisplayRentedScooterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.scooterRentalApp.common.ValidationUtils.isUncorrectedEmail;

@Service
public class DisplayRentedScooterServiceServiceImpl extends AbstractCommonService implements DisplayRentedScooterService {

    private UserAccountRepository userAccountRepository;

    public DisplayRentedScooterServiceServiceImpl(MsgSource msgSource, UserAccountRepository userAccountRepository) {
        super(msgSource);
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> displayRentedScooter(String userEmail) {
        if (isUncorrectedEmail(userEmail)) {
            throw new CommonBadRequestException(msgSource.ERR002);
        }
        UserAccount userAccount = extractUserAccountFromRepository(userEmail);
        if(userAccount.getScooter() == null){
            throw new CommonBadRequestException(msgSource.ERR014);
        }
        return ResponseEntity.ok(new DisplayRentedScooterResponse(msgSource.OK007, userAccount.getScooter()));
    }

    private UserAccount extractUserAccountFromRepository(String userEmail) {
        List<UserAccount> userAccounts = userAccountRepository.findByOwnerEmail(userEmail);
        if (userAccounts.isEmpty()) {
            throw new CommonConflictException(msgSource.ERR006);
        }
        return userAccounts.get(0);
    }

}

