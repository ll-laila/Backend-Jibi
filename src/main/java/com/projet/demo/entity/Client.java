package com.projet.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Client extends User {

    @Column(nullable = false)
    private String cin;
    private String address;
    @Column(name = "new_password")
    private String newPassword;

    @Column(name = "is_first_login")
    private Boolean isFirstLogin ;
    @Column(name = "creation_date")
    private LocalDate creationDate;


    //private String CommercialRn ;
    //private String patentNumber ;
    @Column(name = "is_payment_account_on")
    private Boolean isPaymentAccountOn ;
    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "verification_code_time")
    private String VerificationCodeTime;

    @ManyToOne
    private User verifiedBy;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentAccountId", referencedColumnName = "paymentAccountId")
    private PaymentAccount paymentAccount;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts = new ArrayList<>();


    @OneToMany(mappedBy = "client")
    List<Operation> operations;

    @Column(name = "birth_date")
    private Date birthDate;


    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

 // @Column(nullable = false)
   //private boolean enabled;
    @Column(name = "first_login")
    private boolean firstLogin;

}

