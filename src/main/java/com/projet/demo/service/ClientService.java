package com.projet.demo.service;

import com.projet.demo.dto.AgentDTO;
import com.projet.demo.dto.ClientDTO;
import com.projet.demo.dto.ClientWithPaymentAccountDTO;
import com.projet.demo.dto.PaymentAccountDTO;
import com.projet.demo.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<AgentDTO> getAllCreditors();

    PaymentAccountDTO getPaymentAccountByClientId(long id);

    ClientDTO getClientById(long id);

    ClientDTO getClientByPhoneNumber(String phoneNumber);

}
