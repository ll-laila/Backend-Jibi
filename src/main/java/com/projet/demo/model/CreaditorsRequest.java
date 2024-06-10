package com.projet.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreaditorsRequest {

    private Long id;
    private String firstName;
    private String lastName ;
    private String email ;
    private String phoneNumber ;
    private String image;

}
