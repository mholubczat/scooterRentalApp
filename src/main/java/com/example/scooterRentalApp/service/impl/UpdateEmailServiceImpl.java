package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonBadRequestException;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.UpdateEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

import static com.example.scooterRentalApp.common.ValidationUtils.isUncorrectedEmail;
@Service
public class UpdateEmailServiceImpl extends AbstractCommonService implements UpdateEmailService {
    private UserAccountRepository userAccountRepository;

    public UpdateEmailServiceImpl(MsgSource msgSource, UserAccountRepository userAccountRepository) {
        super(msgSource);
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> updateEmail(Long userId, String userEmail) {
        if (isUncorrectedEmail(userEmail)) {
            throw new CommonBadRequestException(msgSource.ERR002);
        }
        UserAccount userAccount = extractUserAccountFromRepository(userId);
        userAccount.setOwnerEmail(userEmail);
        userAccountRepository.save(userAccount);
        return ResponseEntity.ok(BasicResponse.of(msgSource.OK009));
    }

    private UserAccount extractUserAccountFromRepository(Long accountId) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(accountId);
        if (!optionalUserAccount.isPresent()) {
            throw new CommonConflictException(msgSource.ERR006);
        }
        return optionalUserAccount.get();
    }
}
