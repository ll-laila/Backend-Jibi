package com.projet.demo.services;

import com.projet.demo.model.ClientProfileResponse;
import com.projet.demo.model.ClientRequest;
import com.projet.demo.model.RegisterAgentResponse;

public interface ClientServiceV2 {

    long getAccountId(long id);

    ClientProfileResponse getAccount(long id);


    RegisterAgentResponse changePassword(ClientRequest request);
}
