package com.projet.demo.services;

import com.projet.demo.model.*;

import java.util.List;

public interface ClientService {

    long getAccountId(long id);

    ClientProfileResponse getAccount(long id);

    RegisterAgentResponse changePassword(ClientRequest request);

    List<CreaditorsRequest> getAllCreditors();

    ClientProfileResponse getClientById(long id);
    PaymentAccountResponse getPaymentAccountByClientId(long id);

    ClientProfileResponse getClientByPhoneNumber(String phoneNumber);

    List<AgentServiceResponse> getAllServicesByAgentId(Long agentId);

    List<OperationResponse> getClientOperation(String phoneNumber);
}
