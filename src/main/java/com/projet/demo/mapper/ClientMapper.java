package com.projet.demo.mapper;

import com.projet.demo.entity.Client;
import com.projet.demo.model.ClientProfileResponse;

public class ClientMapper {

    static public ClientProfileResponse ConvertToDto(Client client){
        return ClientProfileResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

}
