package com.projet.demo.controller;


import com.projet.demo.model.*;
import com.projet.demo.services.ClientService;
import com.projet.demo.services.CmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@RequestMapping("/cmi/service")
@PreAuthorize("hasRole('CLIENT')")
public class CMIServices {


    @Autowired
    private CmiService cmiService;

    @Autowired
    private  ClientService clientservice;


    @GetMapping("/allCreditors")
    @PreAuthorize("hasAuthority('client:read')")
    public List<AgentResposne> allCreditors() {
        return clientservice.getAllCreditors();
    }


    @PutMapping("/feedAccount")
    @PreAuthorize("hasAuthority('client:update')")
    public FeedAccountResponse feedPaymentAccount(@RequestBody FeedAccountRequest feedAccountRequest){
        return cmiService.feedPaymentAccount(feedAccountRequest);
    }

    @PutMapping("/payService")
    @PreAuthorize("hasAuthority('client:update')")
    public PaymentResponse payCreditorService(@RequestBody PaymentRequest paymentRequest){
        return cmiService.payCreditorService(paymentRequest);
    }




}
