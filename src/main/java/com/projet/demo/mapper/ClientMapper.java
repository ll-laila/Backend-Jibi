package com.projet.demo.mapper;

import com.projet.demo.dto.ClientDTO;
import com.projet.demo.entity.Client;

public class ClientMapper {

    static public ClientDTO ConvertToDto(Client client){
        return ClientDTO.builder()
                .id(Math.toIntExact(client.getId()))
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .address(client.getAddress())
                .cin(client.getCin())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .password(client.getPassword())
                .build();
    }
}
