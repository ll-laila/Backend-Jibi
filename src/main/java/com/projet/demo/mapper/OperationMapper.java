package com.projet.demo.mapper;

import com.projet.demo.entity.Operation;
import com.projet.demo.model.OperationResponse;


public class OperationMapper {

    static public OperationResponse ConvertToDto(Operation operation){
        return OperationResponse.builder()
                .id(operation.getId())
                .clientName(operation.getClient().getLastName()+" "+operation.getClient().getLastName())
                .creditorName(operation.getCreditorName())
                .serviceName(operation.getServiceName())
                .amount(operation.getAmount())
                .doItAt(operation.getDoItAt())
                .build();
    }

}
