package com.projet.demo.service;

import com.projet.demo.dto.ClientDTO;
import com.projet.demo.dto.ClientWithPaymentAccountDTO;
import com.projet.demo.dto.PaymentAccountDTO;
import com.projet.demo.entity.Client;

import java.util.Optional;

public interface ClientService {
    public PaymentAccountDTO getPaymentAccount(long id);
    public Optional<Client> getAccountById(long id);
    public ClientDTO getAccountByPhoneNumber(String phoneNumber);
    Client getClientByPhoneNumber(String phone);

    ClientWithPaymentAccountDTO getClientWithPaymentAccount(long id);
    public ClientDTO getClientById(long id);
}
