package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.RemoveAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RemoveAccountServiceImpl extends AbstractCommonService implements RemoveAccountService {

    private UserAccountRepository userAccountRepository;

    public RemoveAccountServiceImpl(MsgSource msgSource, UserAccountRepository userAccountRepository) {
        super(msgSource);
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> removeAccount(String userEmail) {
        UserAccount userAccount = extractUserAccountFromRepository(userEmail);
        checkUserAccountAlreadyHaveAnyRental(userAccount);
        userAccountRepository.delete(userAccount);
        return ResponseEntity.ok(BasicResponse.of(msgSource.OK008));
    }

    private UserAccount extractUserAccountFromRepository(String userEmail) {
        List<UserAccount> userAccounts = userAccountRepository.findByOwnerEmail(userEmail);
        if (userAccounts.isEmpty()) {
            throw new CommonConflictException(msgSource.ERR006);
        }
        return userAccounts.get(0);
    }

    private void checkUserAccountAlreadyHaveAnyRental(UserAccount userAccount) {
        if (userAccount.getScooter() != null) {
            throw new CommonConflictException(msgSource.ERR015);
        }
    }
}
