package com.projet.demo.service;

import com.projet.demo.dto.AgentDTO;
import com.projet.demo.dto.ClientDTO;
import com.projet.demo.entity.Agent;
import com.projet.demo.entity.Client;
import com.projet.demo.mapper.AgentMapper;
import com.projet.demo.mapper.ClientMapper;
import com.projet.demo.repository.AgentRepo;
import com.projet.demo.repository.ClientRepo;

import com.vonage.client.sms.messages.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AgentService {

    @Autowired
    private ClientRepo repository;
 //   private PasswordEncoder passwordEncoder;
    private final String BRAND_NAME = "NXSMS";
    @Autowired
    private VonageClient vonageClient;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }


    public String registerClient(Client request) {
        System.out.println("Received request to register client: " + request);

        if (repository.existsByPhoneNumber(request.getPhoneNumber()) || repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email or Phone already exists");
        }

        String generatedPassword = generatePassword();
        var client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .password(generatePassword())
                .cin(request.getCin())
                .isFirstLogin(true)
                .isPaymentAccountOn(false)
                .createdAt(LocalDateTime.now())
                .birthDate(request.getBirthDate())
                .build();
        var savedClient = repository.save(client);

        System.out.println("Client saved successfully: " + savedClient);
        return "success " + generatedPassword;
    }

    public String updateClient(Long id, Client client) {
        // Find the client by ID
        Optional<Client> optionalClient = repository.findById(id);

        if (optionalClient.isPresent()) {
            Client existingClient = optionalClient.get();
            existingClient.setFirstName(client.getFirstName());
            existingClient.setLastName(client.getLastName());
            existingClient.setEmail(client.getEmail());
            existingClient.setAddress(client.getAddress());
            existingClient.setCin(client.getCin());
            existingClient.setPhoneNumber(client.getPhoneNumber());
            existingClient.setBirthDate(client.getBirthDate());

            // Save the updated client
            repository.save(existingClient);
            return "Client updated successfully";
        } else {
            // Client with the given ID not found
            return "Client with ID " + id + " not found";
        }
    }


    public String deleteClient(Long id) {
        Optional<Client> optionalClient = repository.findById(id);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            repository.delete(client);
            return "Client deleted successfully";
        } else {
            return "Client with ID " + id + " not found";
        }
    }

    public List<ClientDTO> getAllClient() {
        List<Client> clients = repository.findAll();
        return clients.stream()
                .map(ClientMapper::ConvertToDto)
                .collect(Collectors.toList());
    }

    public Client getClient(Long clientId) {
        return repository.findById(clientId).orElse(null);
    }

}
