package com.projet.demo.service.impl;

import com.projet.demo.dto.AgentDTO;
import com.projet.demo.entity.Agent;
import com.projet.demo.mapper.AgentMapper;
import com.projet.demo.repository.AgentRepo;
import com.projet.demo.service.AgentService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepo agentRepo;

    public AgentServiceImpl(AgentRepo agentRepo) {
        this.agentRepo = agentRepo;
    }


    @Override
    public List<AgentDTO> getAllAgents() {
        List<Agent> agents = agentRepo.findAll();
        return agents.stream()
                .map(AgentMapper::ConvertToDto)
                .collect(Collectors.toList());
    }

}