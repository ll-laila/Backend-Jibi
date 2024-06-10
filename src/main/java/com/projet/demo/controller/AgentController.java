package com.projet.demo.controller;

import com.projet.demo.entity.Client;
import com.projet.demo.model.*;
import com.projet.demo.services.AgentService;
import com.projet.demo.services.ClientService;
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

    private final AgentService service;
    private final AgentServicesService agentservice;
    private final ClientService clientService;

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
    public RegisterAgentResponse deleteClient(@PathVariable("id") Long id) {
        return service.deleteClient(id);
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


    @GetMapping("/serviceByAgent/{agentId}")
    @PreAuthorize("hasAuthority('agent:read')")
    public List<AgentServiceResponse> getServicesByAgent(@PathVariable("agentId") Long agentId) {
        return agentservice.getAllServicesByAgentId(agentId);
    }



    @GetMapping("/agentByPhone/{phoneNumber}")
    @PreAuthorize("hasAuthority('agent:read')")
    public AgentResposne getClientByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber){
        return  service.getAgentByPhoneNumber(phoneNumber);
    }



    @GetMapping("/operations/{id}")
    @PreAuthorize("hasAuthority('agent:read')")
    public ResponseEntity<List<OperationResponse>> getAgentOperations(@PathVariable Long id) {
        List<OperationResponse> operations = service.getAgentOperation(id);
        return ResponseEntity.ok(operations);
    }

    @GetMapping("/serviceById/{serviceId}")
    @PreAuthorize("hasAuthority('agent:read')")
    public ResponseEntity<AgentServiceResponse> getServiceById(@PathVariable Long serviceId) {
        AgentServiceResponse serviceResponse = agentservice.getServiceById(serviceId);
        if (serviceResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serviceResponse);
    }
}


