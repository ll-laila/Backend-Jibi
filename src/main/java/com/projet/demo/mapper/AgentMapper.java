package com.projet.demo.mapper;


import com.projet.demo.dto.AgentDTO;
import com.projet.demo.entity.Agent;
import jakarta.persistence.Column;

public class AgentMapper {
    static public AgentDTO ConvertToDto(Agent agent){
        return AgentDTO.builder()
                .id(String.valueOf(agent.getId()))
                .firstName(agent.getFirstName())
                .lastName(agent.getLastName())
                .address(agent.getAddress())
                .phoneNumber(agent.getPhoneNumber())
                .email(agent.getEmail())
                .image(agent.getImage())
                .build();
    }
}