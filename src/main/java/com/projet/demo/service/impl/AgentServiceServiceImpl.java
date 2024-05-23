package com.projet.demo.service.impl;


import com.projet.demo.dto.AgentServiceDTO;
import com.projet.demo.entity.AgentService;
import com.projet.demo.mapper.AgentServiceMapper;
import com.projet.demo.repository.AgentServiceRepo;
import com.projet.demo.service.AgentServiceService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class AgentServiceServiceImpl implements AgentServiceService {

    private final AgentServiceRepo agentServiceRepo;

    public AgentServiceServiceImpl(AgentServiceRepo agentServiceRepo) {
        this.agentServiceRepo = agentServiceRepo;
    }


    @Override
    public List<AgentServiceDTO> getAllServices() {
        List<com.projet.demo.entity.AgentService> agents = agentServiceRepo.findAll();
        return agents.stream()
                .map(AgentServiceMapper::ConvertToDto)
                .collect(Collectors.toList());
    }

    @Override

    public List<AgentServiceDTO> getAllServicesByAgentId(Long agentId) {
        List<AgentService> servicesAgent = agentServiceRepo.findAllByAgentId(agentId);
        return servicesAgent.stream()
                .map(AgentServiceMapper::ConvertToDto)
                .collect(Collectors.toList());
    }
}
