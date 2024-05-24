package com.projet.demo.controller;


import com.projet.demo.dto.AgentDTO;
import com.projet.demo.dto.ClientDTO;
import com.projet.demo.dto.ClientWithPaymentAccountDTO;
import com.projet.demo.dto.PaymentAccountDTO;
import com.projet.demo.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @GetMapping("/allCreditors")
    public List<AgentDTO> getCreditors() {
        return clientService.getAllCreditors();
    }


    @GetMapping("/PaymentAccount/{id}")
    public PaymentAccountDTO getPaymentAccount(@PathVariable("id")  long id ){
        return  clientService.getPaymentAccountByClientId(id);
    }

    @GetMapping("/Profile/{id}")
    public ClientDTO getClient(@PathVariable("id")  long id ){
        return  clientService.getClientById(id);
    }

    @GetMapping("/Profile/Phone/{phoneNumber}")
    public ClientDTO getClientByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber){
        return  clientService.getClientByPhoneNumber(phoneNumber);
    }


}
