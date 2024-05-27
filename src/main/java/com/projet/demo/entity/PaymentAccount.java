package com.projet.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "paymentAccountId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PaymentAccount {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long paymentAccountId;

    private double accountBalance;

    private String type;


    @JsonManagedReference
    @OneToOne(mappedBy = "paymentAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Client client;

    private String bankName;


}

