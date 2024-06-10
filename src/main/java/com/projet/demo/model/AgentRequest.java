package com.projet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentRequest {
    private Integer id;
    private String firstName;
    private String lastName ;
    private String address ;
    private String email ;
    private String phoneNumber ;
    private String commercialRn;
    private String patentNumber ;
    private String password ;
    private String cin;
    private Date birthDate;
    private String image;
}
