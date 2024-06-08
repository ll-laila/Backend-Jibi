package com.projet.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projet.demo.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agentId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String CIN;
    private Date birthDate ;
    private String newPassword;
    private String phoneNumber;
    @JsonIgnore
    private String password;
    private Boolean isFirstLogin ;
    private LocalDate createdDate;


    private String CommercialRn ;
    private String patentNumber ;
    private Boolean isPaymentAccountActivated ;
    private String verificationCode;
    private String VerificationCodeCreatedAt;
    private String image;


    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentAccountId", referencedColumnName = "paymentAccountId")
    private PaymentAccount paymentAccount;

    @OneToOne
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "client")
    private List<Operation> operations;


    @Enumerated(EnumType.STRING)
    private Role role;



    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Token> tokens;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }



}
