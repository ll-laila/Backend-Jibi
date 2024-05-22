package com.projet.demo.service.impl;


import com.projet.demo.entity.Agent;
import com.projet.demo.repository.AgentRepo;
import com.projet.demo.service.AgentService;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;

@Data
@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepo agentRepo;

    public AgentServiceImpl(AgentRepo agentRepo) {
        this.agentRepo = agentRepo;
    }

    @Override
    public Agent addAgent(Agent agent) {
        return agentRepo.save(agent);
    }

    @Override
    public void deleteAgentById(Integer agentId) {
        agentRepo.deleteById(agentId);
    }

    @Override
    public Agent updateAgent(Agent agent, Integer agentId) {
        Agent existingAgent = agentRepo.getReferenceById(agentId);
        if (existingAgent != null) {
            existingAgent.setFirstName(agent.getFirstName());
            existingAgent.setLastName(agent.getLastName());
            existingAgent.setAddress(agent.getAddress());
            existingAgent.setEmail(agent.getEmail());
            existingAgent.setPhoneNumber(agent.getPhoneNumber());
            existingAgent.setImmNumber(agent.getImmNumber());
            existingAgent.setPatentNumber(agent.getPatentNumber());
            existingAgent.setPassword(agent.getPassword());
            existingAgent.setIdentityNumber(agent.getIdentityNumber());
            //existingAgent.setFilePath(agent.getFilePath());
            return agentRepo.save(existingAgent);
        } else {
            return null;
        }
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentRepo.findAll();
    }

    @Override
    public Agent getAgent(Integer agentId) {
        return agentRepo.findById(agentId).orElse(null);
    }
}
