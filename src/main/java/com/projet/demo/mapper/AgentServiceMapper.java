package com.projet.demo.mapper;

import com.projet.demo.dto.AgentServiceDTO;
import com.projet.demo.entity.AgentService;

public class AgentServiceMapper {

    static public AgentServiceDTO ConvertToDto(AgentService agentService){
        return AgentServiceDTO.builder()
                .id(Integer.valueOf(String.valueOf(agentService.getId())))
                .name(agentService.getName())
                .type(AgentServiceDTO.ServiceType.valueOf(agentService.getType()))
                .build();
    }

}
