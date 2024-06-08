package com.projet.demo.controller;

import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.services.AgentService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/services/agent")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('AGENT')")
public class CMIServiceV2 {
}
