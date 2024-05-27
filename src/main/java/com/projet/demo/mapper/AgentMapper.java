package com.projet.demo.mapper;

import com.projet.demo.entity.Client;
import com.projet.demo.model.AgentResposne;

public class AgentMapper {

    static public AgentResposne ConvertToDto(Client agent){
        return AgentResposne.builder()
                .id(agent.getId())
                .firstname(agent.getFirstName())
                .lastname(agent.getLastName())
                .email(agent.getEmail())
                .address(agent.getAddress())
                .phoneNumber(agent.getPhoneNumber())
                .image(agent.getCIN())
                .build();
    }


}
