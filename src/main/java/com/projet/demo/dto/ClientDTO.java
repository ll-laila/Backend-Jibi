package com.projet.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String cin;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private double balance;
    private LocalDate paymentAccountCreatedDate;

}
