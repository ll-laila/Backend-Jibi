package com.projet.demo.service;


import com.projet.demo.dto.ClientDTO;
import com.projet.demo.dto.PaymentAccountDTO;
import com.projet.demo.entity.Client;
import com.projet.demo.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepo clientRepository;

    public PaymentAccountDTO getPaymentAccount(long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));
        return PaymentAccountDTO.builder().id(Math.toIntExact(client.getPaymentAccount().getPaymentAccountId()))
                .balance(client.getPaymentAccount().getBalance()).createdDate(client.getPaymentAccount().getCreatedDate()).build();
    }

    public Optional<Client> getAccountById(long id) {
        return clientRepository.findById(id);

    }

    public ClientDTO getAccountByPhoneNumber(String phoneNumber) {
        Client client = clientRepository.findByPhoneNumber(phoneNumber);
        return ClientDTO.builder().id(Math.toIntExact(client.getId())).firstName(client.getFirstName()).lastName(client.getLastName()).cin(client.getCin())
                .phoneNumber(client.getPhoneNumber()).email(client.getEmail()).build();
    }

}
