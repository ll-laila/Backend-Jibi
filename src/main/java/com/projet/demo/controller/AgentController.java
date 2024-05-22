package com.projet.demo.controller;

import com.projet.demo.dto.AgentDTO;
import com.projet.demo.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {

    @Autowired
    private AgentService agentservice;

    @GetMapping("/allAgents")
    @ResponseStatus(HttpStatus.OK)
    public List<AgentDTO> getAllAgents(){
        return agentservice.getAllAgents();
    }

}
