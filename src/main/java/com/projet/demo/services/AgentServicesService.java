package com.projet.demo.services;

import com.projet.demo.entity.AgentService;
import com.projet.demo.entity.Client;
import com.projet.demo.entity.Role;
import com.projet.demo.mapper.AgentServiceMapper;
import com.projet.demo.mapper.ClientMapper;
import com.projet.demo.model.AgentServiceRequest;
import com.projet.demo.model.AgentServiceResponse;
import com.projet.demo.model.ClientProfileResponse;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.repository.AgentServiceRepository;
import com.projet.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentServicesService {

    private final AgentServiceRepository agentServiceRepository;
    private final ClientRepository repository;

    public List<AgentServiceResponse> getAllServicesByAgentId(Long agentId) {
        List<AgentService> servicesAgent = agentServiceRepository.findAllByAgentId(agentId);
        return servicesAgent.stream()
                .map(AgentServiceMapper::ConvertToDto)
                .collect(Collectors.toList());
    }

    public AgentServiceResponse getServiceById(Long id) {
        return agentServiceRepository.findById(id)
                .map(AgentServiceMapper::ConvertToDto)
                .orElse(null);
    }



    public RegisterAgentResponse createService(AgentServiceRequest request, Long id ) {
        // Trouver l'agent par son numéro de téléphone
        Client agent = repository.findAgentByClientId(id);

        // Vérifier si l'agent existe
        if (agent == null) {
            // Gérer le cas où l'agent n'est pas trouvé
            throw new RuntimeException("Agent not found");
        }

        // Créer le service avec l'agent trouvé
        AgentService agentService = AgentService.builder()
                .name(request.getName())
                .type(request.getType())
                .agent(agent)
                .build();

        // Enregistrer le service
        agentServiceRepository.save(agentService);

        // Retourner la réponse
        return RegisterAgentResponse.builder().message("Service created successfully").build();
    }

    public RegisterAgentResponse updateService(Long serviceId, AgentServiceRequest request) {
        AgentService agentService = agentServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        agentService.setName(request.getName());
        agentService.setType(request.getType());

        agentServiceRepository.save(agentService);

        return RegisterAgentResponse.builder().message("Service updated successfully").build();
    }

    public RegisterAgentResponse deleteService(Long serviceId) {
        AgentService agentService = agentServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        agentServiceRepository.delete(agentService);

        return RegisterAgentResponse.builder().message("Service deleted successfully").build();
    }
}
