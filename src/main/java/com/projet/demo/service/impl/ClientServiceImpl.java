package com.projet.demo.service.impl;


import com.projet.demo.dto.ClientDTO;
import com.projet.demo.dto.ClientWithPaymentAccountDTO;
import com.projet.demo.dto.PaymentAccountDTO;
import com.projet.demo.entity.Client;
import com.projet.demo.entity.PaymentAccount;
import com.projet.demo.repository.ClientRepo;
import com.projet.demo.repository.PaymentAccountRepo;
import com.projet.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepo clientRepository;

    //private PaymentAccountRepo paymentAccountRepository;
    public PaymentAccountDTO getPaymentAccount(long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));
        return PaymentAccountDTO.builder().id(Math.toIntExact(client.getPaymentAccount().getPaymentAccountId()))
                .balance(client.getPaymentAccount().getBalance()).createdDate(client.getPaymentAccount().getCreatedDate()).build();
    }

    public Optional<Client> getAccountById(long id) {
        return clientRepository.findById(id);

    }


    @Override

    public ClientDTO getAccountByPhoneNumber(String phoneNumber) {
        Client client = clientRepository.findByPhoneNumber(phoneNumber);
        return ClientDTO.builder().id(Math.toIntExact(client.getId())).firstName(client.getFirstName()).lastName(client.getLastName()).cin(client.getCin())
                .phoneNumber(client.getPhoneNumber()).email(client.getEmail()).build();
    }


    @Override
    public ClientWithPaymentAccountDTO getClientWithPaymentAccount(long id) {
        Client client = clientRepository.getReferenceById(id);

        return ClientWithPaymentAccountDTO.builder()
                .clientId(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .cin(client.getCin())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .balance(client.getPaymentAccount().getBalance())
                .paymentAccountCreatedDate(client.getPaymentAccount().getCreatedDate())
                .build();
//        return client.stream()
//                    .map(ClientWithPaymentAccountMapper::ConvertToDto)
//                        .collect(Collectors.toList());
    }
    public ClientDTO getClientById(long id) {
        Client client = clientRepository.findClientByClientId(id);
        return ClientDTO.builder().id(Math.toIntExact(client.getId())).firstName(client.getFirstName()).lastName(client.getLastName()).build();
    }
    public ClientWithPaymentAccountDTO getClientPaymentAccountBalance(long id) {
        Client client = clientRepository.findClientByClientId(id);
        //PaymentAccount paymentAccount = paymentAccountRepository.getByClientId(id);
        return ClientWithPaymentAccountDTO.builder().clientId(client.getId()).balance(client.getPaymentAccount().getBalance()).build();
        //.balance(client.getBalance())
    }
}
