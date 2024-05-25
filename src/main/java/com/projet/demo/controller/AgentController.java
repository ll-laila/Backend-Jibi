package com.projet.demo.controller;

import com.projet.demo.model.User;
import com.projet.demo.model.ClientRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.services.AgentService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('agent:read')")
    public List<User> getAllClient() {
        return service.findAll();
    }

    @GetMapping("/client/{id}")
    @PreAuthorize("hasAuthority('agent:read')")
    public User getById(@PathVariable("id") Long id) {
        // Logic to find user by ID
        User User = service.findById(id);
        // Return the found user or handle the case where user is not found
        return User;
    }


    @PostMapping("/register")
    @PreAuthorize("hasAuthority('agent:create')")
    @Hidden
    public ResponseEntity<RegisterAgentResponse> registerClient(
            @RequestBody ClientRequest request
    ) {
        return ResponseEntity.ok(service.registerClient(request));
    }



    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('agent:update')")
    public ResponseEntity<RegisterAgentResponse> updateClient(@PathVariable("id") Long id, @RequestBody ClientRequest updatedAgent)  {
        return ResponseEntity.ok(service.updateClient(id,updatedAgent));
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('agent:delete')")
    public RegisterAgentResponse deleteClient(@PathVariable("id") Long id) { return service.deleteClient(id);}}
