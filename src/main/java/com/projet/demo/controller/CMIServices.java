package com.projet.demo.controller;


import com.projet.demo.model.FeedAccountRequest;
import com.projet.demo.model.FeedAccountResponse;
import com.projet.demo.model.PaymentRequest;
import com.projet.demo.model.PaymentResponse;
import com.projet.demo.services.CmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/fim/est3Dgate")
@PreAuthorize("hasRole('CLIENT')")
public class CMIServices {


    @Autowired
    private CmiService cmiService;

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
