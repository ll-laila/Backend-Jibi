package com.projet.demo.service;

import com.projet.demo.dto.ClientDTO;
import com.projet.demo.dto.ClientWithPaymentAccountDTO;
import com.projet.demo.dto.PaymentAccountDTO;
import com.projet.demo.entity.Client;

import java.util.Optional;

public interface ClientService {
    PaymentAccountDTO getPaymentAccount(long id);
    Optional<Client> getAccountById(long id);
    ClientDTO getAccountByPhoneNumber(String phoneNumber);
    //Client getClientByPhoneNumber(String phone);

    ClientWithPaymentAccountDTO getClientWithPaymentAccount(long id);
    public ClientDTO getClientById(long id);
}
