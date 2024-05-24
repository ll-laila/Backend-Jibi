package com.projet.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName ;
    private String address ;
    private String email ;
    @Column(unique = true)
    private String identityNumber;
    private String phoneNumber ;
    @Column(unique = true)
    private String ImmNumber ;
    private String patentNumber ;
    private String password ;
    private String image;


    //private String filePath;

}
