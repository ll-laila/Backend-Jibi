package com.projet.demo.mapper;

import com.projet.demo.entity.AgentService;
import com.projet.demo.model.AgentServiceResponse;

public class AgentServiceMapper {

    static public AgentServiceResponse ConvertToDto(AgentService agentService){
        return AgentServiceResponse.builder()
                .id(agentService.getId())
                .name(agentService.getName())
                .type(agentService.getType())
                .build();
    }


}
