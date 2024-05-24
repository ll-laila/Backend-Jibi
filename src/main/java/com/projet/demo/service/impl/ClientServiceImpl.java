package com.projet.demo.service.impl;


import com.projet.demo.dto.AgentDTO;
import com.projet.demo.dto.ClientDTO;
import com.projet.demo.dto.ClientWithPaymentAccountDTO;
import com.projet.demo.dto.PaymentAccountDTO;
import com.projet.demo.entity.Agent;
import com.projet.demo.entity.Client;
import com.projet.demo.entity.PaymentAccount;
import com.projet.demo.mapper.AgentMapper;
import com.projet.demo.mapper.ClientMapper;
import com.projet.demo.mapper.PaymentAccountMapper;
import com.projet.demo.repository.AgentRepo;
import com.projet.demo.repository.ClientRepo;
import com.projet.demo.repository.PaymentAccountRepo;
import com.projet.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepo clientRepository;

    @Autowired
    private PaymentAccountRepo paymentAccountRepo;

    @Autowired
    private  AgentRepo agentRepo;


    @Override
    public List<AgentDTO> getAllCreditors() {
        List<Agent> agents = agentRepo.findAll();
        return agents.stream()
                .map(AgentMapper::ConvertToDto)
                .collect(Collectors.toList());
    }

    public PaymentAccountDTO getPaymentAccountByClientId(long id) {
        PaymentAccount paymentAccount = paymentAccountRepo.findPaymentAccountByClientId(id);
        if (paymentAccount != null) {
            return PaymentAccountMapper.ConvertToDto(paymentAccount);
        }
        return null;
    }

    @Override
    public ClientDTO getClientById(long id) {
        Client client = clientRepository.findClientById(id);
        if (client != null) {
            return ClientMapper.ConvertToDto(client);
        }
        return null;
    }


    @Override
    public ClientDTO getClientByPhoneNumber(String phoneNumber) {
        Client client = clientRepository.findClientByPhoneNumber(phoneNumber);
        if (client != null) {
            return ClientMapper.ConvertToDto(client);
        }
        return null;
    }


}
