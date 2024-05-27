package com.projet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentResposne {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String address ;
    private String phoneNumber ;
    private String password;
    private String image;
}
