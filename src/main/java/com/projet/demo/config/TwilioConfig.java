package com.projet.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioConfig {

    @Value("${twilio.AccountSid}")
    private String accountSid;
    @Value("${twilio.AuthToken}")
    private String authToken;
    @Value("${twilio.phoneNumber")
    private String phoneNumber;

}

