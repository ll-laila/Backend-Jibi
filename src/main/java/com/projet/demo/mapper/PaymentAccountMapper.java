package com.projet.demo.mapper;

import com.projet.demo.dto.PaymentAccountDTO;
import com.projet.demo.entity.PaymentAccount;

public class PaymentAccountMapper {

    static public PaymentAccountDTO ConvertToDto(PaymentAccount paymentAccount){
        return PaymentAccountDTO.builder()
                .id(Math.toIntExact(paymentAccount.getPaymentAccountId()))
                .balance(paymentAccount.getBalance())
                .createdDate(paymentAccount.getCreatedDate())
                .build();
    }

}
