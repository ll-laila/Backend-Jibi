package com.projet.demo.vonageConfig;

import com.vonage.client.VonageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VonageConfig {
    @Value("2053ed34")
    private String apiKey;

    @Value("j2Cy3qjnDhKlnCbi")
    private String apiSecret;
    @Bean
    public VonageClient vonageClient() {
        return VonageClient.builder().apiKey(apiKey).apiSecret(apiSecret).build();
    }
}
