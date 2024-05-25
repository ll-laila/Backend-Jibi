package com.projet.demo.services;

import com.projet.demo.model.User;
import com.projet.demo.model.ClientProfileResponse;
import com.projet.demo.model.ClientRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceV2Impl implements ClientServiceV2 {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private  PasswordEncoder passwordEncoder;


    @Override
    public long getAccountId(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
      return user.getPaymentAccount().getPaymentAccountId();
    }

    @Override
    public ClientProfileResponse getAccount(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return ClientProfileResponse.builder().firstName(user.getFirstName()).lastName(user.getLastName()).CIN(user.getCIN())
                .phoneNumber(user.getPhoneNumber()).email(user.getEmail()).address(user.getAddress()).build();  }

    public RegisterAgentResponse changePassword(ClientRequest request) {

        User user = userRepo.findByPhoneNumber(request.getPhoneNumber());
        if (!(user == null) ) {
             user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            user.setIsFirstLogin(false);
            userRepo.save(user);
            return RegisterAgentResponse.builder().message("Password updated successfully").build();
            } else {
            return RegisterAgentResponse.builder().message("User not found").build();
            }
        }



    }








