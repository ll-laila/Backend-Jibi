package com.projet.demo.controller;


import com.projet.demo.dto.AgentDTO;
import com.projet.demo.entity.Agent;
import com.projet.demo.service.AdminService;

import com.projet.demo.service.SmsService;
import com.projet.demo.twilio.OtpResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;


@CrossOrigin(origins = "http://localhost:4200/")
@AllArgsConstructor
@RestController
@RequestMapping("/api/agents")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    private final SmsService smsService;


    @GetMapping("/allAgents")
    public List<AgentDTO> getAllAgent() {
        return adminService.getAllAgent();
    }

    @PostMapping
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) {
        String otp = generateOTP();
        agent.setPassword(otp);
        System.out.println(agent.getPassword());
        Agent createdAgent = adminService.addAgent(agent);
        String otpMessage = "Welcome " + createdAgent.getFirstName() + "! Your account has been created. Your temporary password is: " + otp;
        OtpResponseDto stat=smsService.sendSMS(createdAgent,createdAgent.getPhoneNumber(), otpMessage);
        System.out.println(stat);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAgent);
    }
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

    @GetMapping
    public List<Agent> getAllAgents(){
        return adminService.getAllAgents();
    }

    @GetMapping("/{agentId}")
    public Agent getAgent(@PathVariable("agentId") Integer id){
        return adminService.getAgent(id);
    }

    @DeleteMapping("/{agentId}")
    public void deleteAgent(@PathVariable("agentId") Integer id){
        adminService.deleteAgentById(id);
    }
    @PutMapping("/{agentId}")
    public ResponseEntity<Agent> updateAgent(@PathVariable("agentId") Integer id, @RequestBody Agent agent) {
        Agent updatedAgent = adminService.updateAgent(agent, id);
        if (updatedAgent != null) {
            return ResponseEntity.ok(updatedAgent);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

}

