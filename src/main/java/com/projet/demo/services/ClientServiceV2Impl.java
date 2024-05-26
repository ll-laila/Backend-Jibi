package com.projet.demo.services;

import com.projet.demo.entity.Client;
import com.projet.demo.model.ClientProfileResponse;
import com.projet.demo.model.ClientRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceV2Impl implements ClientServiceV2 {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;


    @Override
    public long getAccountId(long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));
      return client.getPaymentAccount().getPaymentAccountId();
    }

    @Override
    public ClientProfileResponse getAccount(long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));
        return ClientProfileResponse.builder().firstName(client.getFirstName()).lastName(client.getLastName()).CIN(client.getCIN())
                .phoneNumber(client.getPhoneNumber()).email(client.getEmail()).address(client.getAddress()).build();  }

    public RegisterAgentResponse changePassword(ClientRequest request) {

        Client client = clientRepository.findByPhoneNumber(request.getPhoneNumber());
        if (!(client == null) ) {
             client.setPassword(passwordEncoder.encode(request.getNewPassword()));
            client.setIsFirstLogin(false);
            clientRepository.save(client);
            return RegisterAgentResponse.builder().message("Password updated successfully").build();
            } else {
            return RegisterAgentResponse.builder().message("Client not found").build();
            }
        }



    }








