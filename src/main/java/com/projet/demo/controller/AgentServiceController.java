package com.projet.demo.controller;

import com.projet.demo.dto.AgentServiceDTO;
import com.projet.demo.service.AgentServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class AgentServiceController {

    @Autowired
    private AgentServiceService agentServiceService;

    @GetMapping("/allServices")
    @ResponseStatus(HttpStatus.OK)
    public List<AgentServiceDTO> getAllServices(){
        return agentServiceService.getAllServices();
    }

    @GetMapping("/{agentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AgentServiceDTO> getAllServicesByAgentId(@PathVariable Long agentId){
        return agentServiceService.getAllServicesByAgentId(agentId);
    }


}
