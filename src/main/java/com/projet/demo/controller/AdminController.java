package com.projet.demo.controller;

import com.projet.demo.entity.Client;
import com.projet.demo.model.AgentRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.repository.ClientRepository;
import com.projet.demo.services.AdminService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor

public class AdminController {
    private final AdminService service;

    @GetMapping("/list")
   @PreAuthorize("hasAuthority('admin:read')")
    public List<Client> get() {
        return service.findAll();
    }

    @GetMapping("/agent/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public Client getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public ResponseEntity<Client> registerAgent(
            @RequestBody AgentRequest request
    ) {
        return ResponseEntity.ok(service.registerAgent(request));
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<RegisterAgentResponse> updateUser(@PathVariable("id") Long id, @RequestBody AgentRequest updatedAgent)  {
        return ResponseEntity.ok(service.updateAgent(id,updatedAgent));
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public RegisterAgentResponse deleteUser(@PathVariable("id") Long id)  {
            return service.delete(id);
    }

}
