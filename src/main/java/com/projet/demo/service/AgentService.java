package com.projet.demo.service;

import com.projet.demo.dto.AgentDTO;

import com.projet.demo.entity.Agent;


import java.util.List;

public interface AgentService {

    List<AgentDTO> getAllAgent();

    Agent addAgent(Agent agent);
    void deleteAgentById(Integer agentId);

    Agent updateAgent(Agent agent, Integer agentId);

    List<Agent> getAllAgents();
    Agent getAgent(Integer agentId);
}

