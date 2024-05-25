package com.projet.demo.controller;

import com.projet.demo.model.BankAccount;
import com.projet.demo.model.User;
import com.projet.demo.model.PaymentAccount;
import com.projet.demo.model.OpenPaymentAccountResponse;
import com.projet.demo.model.OpenPaymentAccountTransactionRequest;
import com.projet.demo.repository.*;
import com.projet.demo.services.CreditorService;
import com.vonage.client.VonageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "*") // Allow requests from Angular app's origin
@PreAuthorize("hasAuthority('AGENT')")
@RequestMapping("/fim/est3DgateV2")
public class CMIServiceV2 {

    @Autowired
    BankAccountRepo bankAccountRepo;
    @Autowired
    private PaymentAccountRepo paymentAccountRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private VonageClient vonageClient;
    @Autowired
    private CreditorService creditorService;
    @Autowired
    CustomerOrderRepo customerOrderRepo;
    @Autowired
    OrderItemRepo orderItemRepo;
    @Autowired
    DeliveryRepo deliveryRepository;
    @Autowired
    UserRepo userRepo;
    @PreAuthorize("hasAuthority('agent:create')")
    @PostMapping("/openPaymentAccount/{clientId}")
    public ResponseEntity<OpenPaymentAccountResponse> openPaymentAccount(@PathVariable("clientId") long clientId, @RequestBody OpenPaymentAccountTransactionRequest request) {
        User user = userRepo.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        String cni = user.getCIN();
        BankAccount bankAccount = bankAccountRepo.findByClientCni(cni);

        double accountBalance = bankAccount.getBalance();

        if (accountBalance >= request.getPaymentLimit()) {
            LocalDate currentDate = LocalDate.now();

            PaymentAccount paymentAccount=PaymentAccount.builder()
                    .accountBalance(request.getPaymentLimit())
                    .bankName("CIH")
                    .createdDate(currentDate)
                    .build();
            paymentAccountRepo.save(paymentAccount);
            long paymentAccountId =paymentAccount.getPaymentAccountId();

            user.setIsPaymentAccountActivated(true);
            user.setPaymentAccount(paymentAccount);


            userRepo.save(user);



            return new ResponseEntity<>(OpenPaymentAccountResponse.builder().isOpened(true).build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(OpenPaymentAccountResponse.builder().isOpened(false).build(), HttpStatus.OK);
        }
    }

}
