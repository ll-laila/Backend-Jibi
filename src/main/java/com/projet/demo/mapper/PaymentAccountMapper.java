package com.projet.demo.mapper;

import com.projet.demo.entity.PaymentAccount;
import com.projet.demo.model.PaymentAccountResponse;

public class PaymentAccountMapper {

    static public PaymentAccountResponse ConvertToDto(PaymentAccount paymentAccount){
        return PaymentAccountResponse.builder()
                .id(paymentAccount.getPaymentAccountId())
                .balance(paymentAccount.getAccountBalance())
                .type(paymentAccount.getType())
                .build();
    }


}
