package com.projet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationRequest {
    private double amount ;
    private String creditor ;
    private Date date ;
    private long accountId  ;
    private String description ;
    private String phoneNumber ;
    private String address ;




}
