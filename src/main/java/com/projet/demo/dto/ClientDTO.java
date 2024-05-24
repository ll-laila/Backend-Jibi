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
    private String address;
    private String cin;
    private String email;
    private String phoneNumber;
    private String password;

}
