package com.projet.demo.service;

import com.projet.demo.dto.AgentServiceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AgentServiceService {

    List<AgentServiceDTO> getAllServicesByAgentId(Long agentId);
    List<AgentServiceDTO> getAllServices();

}
