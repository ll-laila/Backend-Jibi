package com.projet.demo.controller;

import com.projet.demo.entity.Client;
import com.projet.demo.model.AgentServiceRequest;
import com.projet.demo.model.ClientRequest;
import com.projet.demo.model.PaymentAccountRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.services.AgentService;
import com.projet.demo.services.ClientService;
import com.projet.demo.services.AgentServicesService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class AgentController {

    private  final AgentService service;
    private final ClientService clientService;
    private final AgentServicesService agentservice;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('agent:read')")
    public List<Client> getAllClient() {
        return service.findAll();
    }

    @GetMapping("/client/{id}")
    @PreAuthorize("hasAuthority('agent:read')")
    public Client getById(@PathVariable("id") Long id) {
        Client Client = service.findById(id);
        // Return the found user or handle the case where user is not found
        return Client;
    }

    @Data
    static class ClientRegistrationRequest {
        private ClientRequest clientRequest;
        private PaymentAccountRequest paymentAccountRequest;

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('agent:update')")
    public ResponseEntity<RegisterAgentResponse> updateClient(@PathVariable("id") Long id, @RequestBody ClientRequest updatedAgent)  {
        return ResponseEntity.ok(service.updateClient(id,updatedAgent));
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('agent:delete')")
    public RegisterAgentResponse deleteClient(@PathVariable("id") Long id) { return service.deleteClient(id);
    }


    @PreAuthorize("hasAuthority('agent:update')")
    @PostMapping("/changePassword")
    public ResponseEntity<RegisterAgentResponse> changePassword(@RequestBody ClientRequest request) {
        RegisterAgentResponse client = clientService.changePassword(request);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/services/{id}")
    @PreAuthorize("hasAuthority('agent:create')")
    @Hidden
    public ResponseEntity<RegisterAgentResponse> createService(
            @PathVariable Long id,
            @RequestBody AgentServiceRequest request
    ) {
        return ResponseEntity.ok(agentservice.createService(request, id));
    }

    @PutMapping("/service/update/{serviceId}")
    @PreAuthorize("hasAuthority('agent:update')")
    public ResponseEntity<RegisterAgentResponse> updateService(
            @PathVariable Long serviceId,
            @RequestBody AgentServiceRequest request
    ) {
        return ResponseEntity.ok(agentservice.updateService(serviceId, request));
    }

    @DeleteMapping("/service/delete/{serviceId}")
    @PreAuthorize("hasAuthority('agent:delete')")
    public RegisterAgentResponse deleteService(@PathVariable Long serviceId) {
        return agentservice.deleteService(serviceId);
    }



}
