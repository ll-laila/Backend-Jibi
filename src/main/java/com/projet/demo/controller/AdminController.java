package com.projet.demo.controller;

import com.projet.demo.model.User;
import com.projet.demo.model.AgentRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.repository.UserRepo;
import com.projet.demo.services.AdminService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor

public class AdminController {
    private final AdminService service;
    private final UserRepo userRepository;

    @GetMapping("/list")
   @PreAuthorize("hasAuthority('admin:read')")
    public List<User> get() {
        return service.findAll();
    }

    @GetMapping("/agent/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public User getById(@PathVariable("id") Long id) {
        User agent = service.findById(id);
        return agent;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public ResponseEntity<RegisterAgentResponse> registerAgent(
            @RequestBody AgentRequest request
    ) {
        return ResponseEntity.ok(service.registerAgent(request));
    }





    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<RegisterAgentResponse> updateUser(@PathVariable("id") Long id, @RequestBody AgentRequest updatedAgent)  {
      return ResponseEntity.ok(service.updateAgent(id,updatedAgent));
    }






    @DeleteMapping("/del")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public RegisterAgentResponse deleteUser(@PathVariable("id") Long id)  {
            return service.delete(id);
    }

}
