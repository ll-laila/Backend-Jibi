package com.projet.demo.services;

import com.projet.demo.model.Creditor;
import com.projet.demo.repository.CreditorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditorServiceImpl implements CreditorService {
    @Autowired
    private CreditorRepo creditorRepo;
    public boolean checkPhoneNumber(String phone  , String name ){
        Creditor creditor = creditorRepo.getByName(name);

        return creditor != null && creditor.getPhoneNumbers().contains(phone);
    }
}
