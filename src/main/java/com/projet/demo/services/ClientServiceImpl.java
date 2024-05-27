package com.projet.demo.services;

import com.projet.demo.entity.Client;
import com.projet.demo.entity.AgentService;
import com.projet.demo.entity.PaymentAccount;
import com.projet.demo.mapper.AgentMapper;
import com.projet.demo.mapper.AgentServiceMapper;
import com.projet.demo.mapper.ClientMapper;
import com.projet.demo.mapper.PaymentAccountMapper;
import com.projet.demo.model.*;
import com.projet.demo.repository.AgentServiceRepository;
import com.projet.demo.repository.ClientRepository;
import com.projet.demo.repository.PaymentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private PaymentAccountRepository paymentAccountRepository;
    @Autowired
    private AgentServiceRepository agentServiceRepository;

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



    @Override
    public List<AgentResposne> getAllCreditors(){
            List<Client> agents = clientRepository.findAllAgentWithRoleClient();

            return agents.stream()
                    .map(AgentMapper::ConvertToDto)
                    .collect(Collectors.toList());
    }


    @Override
    public List<AgentServiceResponse> getAllServicesByAgentId(Long agentId) {
        List<AgentService> servicesAgent = agentServiceRepository.findAllByAgentId(agentId);
        return servicesAgent.stream()
                .map(AgentServiceMapper::ConvertToDto)
                .collect(Collectors.toList());
    }


    @Override
      public PaymentAccountResponse getPaymentAccountByClientId(long id) {
          PaymentAccount paymentAccount = paymentAccountRepository.findPaymentAccountByClientId(id);
          if (paymentAccount != null) {
              return PaymentAccountMapper.ConvertToDto(paymentAccount);
          }
          return null;

      }


        @Override
        public ClientProfileResponse getClientById(long id) {
            Client client = clientRepository.findClientByClientId(id);
            if (client != null) {
                return ClientMapper.ConvertToDto(client);
            }
            return null;
        }

        @Override
        public ClientProfileResponse getClientByPhoneNumber(String phoneNumber) {
            Client client = clientRepository.findByPhoneNumber(phoneNumber);
            if (client != null) {
                return ClientMapper.ConvertToDto(client);
            }
            return null;
        }




}








