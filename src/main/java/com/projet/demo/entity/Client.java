package com.projet.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
public class Client extends User {

    @Column(nullable = false)
    private String cin;

    //private String dob;

    @ManyToOne
    private User verifiedBy;

    /*@OneToOne(mappedBy = "client")
    private PaymentAccount account;

    @OneToMany(mappedBy = "client")
    List<Operation> operations;

    @Enumerated(EnumType.STRING)
    private AccountLimit desiredAccountLimit;*/

}