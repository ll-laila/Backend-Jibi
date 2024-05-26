package com.projet.demo.services;

import com.projet.demo.entity.Creditor;
import com.projet.demo.repository.CreditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditorServiceImpl implements  CreditorService{
    @Autowired
    private CreditorRepository creditorRepository;
    public boolean checkPhoneNumber(String phone  , String name ){
        Creditor creditor = creditorRepository.getByName(name);

        return creditor != null && creditor.getPhoneNumbers().contains(phone);
    }
}
