package com.projet.demo.controller;
import com.projet.demo.entity.Client;
import com.projet.demo.service.AgentService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class AgentController {
    private  final AgentService service;
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
    //@PreAuthorize("hasAuthority('agent:create')")
    @Hidden
    public ResponseEntity<String> registerClient(
            @RequestBody Client request
    ) {
        return ResponseEntity.ok(service.registerClient(request));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClient(@PathVariable("id") Long id, @RequestBody Client updatedAgent)  {
        return ResponseEntity.ok(service.updateClient(id,updatedAgent));
    }


    @DeleteMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id) { return service.deleteClient(id);}

}
