package com.projet.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentServiceDTO {

    private Integer id;
    private String name;
    private ServiceType type;

    public enum ServiceType {
        FACTURE,
        RECHARGE
    }

}
