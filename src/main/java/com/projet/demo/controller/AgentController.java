package com.projet.demo.controller;

import com.projet.demo.entity.Client;
import com.projet.demo.model.AgentServiceRequest;
import com.projet.demo.model.ClientRequest;
import com.projet.demo.model.PaymentAccountRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.services.AgentService;
import com.projet.demo.services.AgentServicesService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
private final AgentServicesService agentservice;
    @Operation(
            description = "Get endpoint for Agent",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )


    @PostMapping("/register")
    @PreAuthorize("hasAuthority('agent:create')")
    @Hidden
    public ResponseEntity<RegisterAgentResponse> registerClient(
            @RequestBody ClientRegistrationRequest registrationRequest
    ) {
        return ResponseEntity.ok(service.registerClient(
                registrationRequest.getClientRequest(),
                registrationRequest.getPaymentAccountRequest()
        ));
    }

    @GetMapping("/listByAgent/{agentId}")
    @PreAuthorize("hasAuthority('agent:read')")
    public List<Client> getClientsByAgent(@PathVariable("agentId") Long agentId) {
        return service.getClientsByAgentId(agentId);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('agent:read')")
    public List<Client> getAllClient() {
        return service.findAll();
    }

    @GetMapping("/client/{id}")
    @PreAuthorize("hasAuthority('agent:read')")
    public Client getById(@PathVariable("id") Long id) {
        // Logic to find user by ID
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
    public ResponseEntity<RegisterAgentResponse> updateClient(
            @PathVariable("id") Long id,
            @RequestBody ClientRegistrationRequest registrationRequest
    ) {
        ClientRequest clientRequest = registrationRequest.getClientRequest();
        PaymentAccountRequest paymentAccountRequest = registrationRequest.getPaymentAccountRequest();

        return ResponseEntity.ok(service.updateClient(id, clientRequest, paymentAccountRequest));
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('agent:delete')")
    public RegisterAgentResponse deleteClient(@PathVariable("id") Long id) { return service.deleteClient(id);
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
