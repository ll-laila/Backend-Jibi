package com.projet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class OperationResponse {

    private Long id;

    private String refOperation;

    private String clientName;

    private String creditorName;

    private String serviceName;

    private double amount;

    private LocalDateTime doItAt;


}
