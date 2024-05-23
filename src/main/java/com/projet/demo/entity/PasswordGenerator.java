package com.projet.demo.entity;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordGenerator {

    public static String generateNumericTemporaryPassword(int length) {
        // Generate a random numeric string of specified length
        return RandomStringUtils.randomNumeric(length);
    }
}