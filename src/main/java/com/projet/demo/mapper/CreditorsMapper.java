package com.projet.demo.mapper;

import com.projet.demo.entity.Client;
import com.projet.demo.model.CreaditorsRequest;

public class CreditorsMapper {


    static public CreaditorsRequest ConvertToDto(Client agent){
        return CreaditorsRequest.builder()
                .id(agent.getId())
                .firstName(agent.getFirstName())
                .lastName(agent.getLastName())
                .email(agent.getEmail())
                .phoneNumber(agent.getPhoneNumber())
                .image(agent.getCin())
                .build();
    }

}
