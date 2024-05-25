package com.projet.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.demo.auth.AuthenticationResponse;
import com.projet.demo.config.JwtService;
import com.projet.demo.model.User;
import com.projet.demo.model.Role;
import com.projet.demo.model.AgentRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.repository.UserRepo;
import com.projet.demo.model.Token;
import com.projet.demo.repository.TokenRepo;
import com.projet.demo.model.TokenType;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "http://localhost:4200") // Allow requests from Angular app's origin
@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepo repository;
    private final TokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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


    private void saveAgentToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(token);
    }
    public RegisterAgentResponse registerAgent(AgentRequest request) {
        if (repository.existsByPhoneNumber(request.getPhoneNumber()) && repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Phone num already exists Or Email");

        }
        String generatedPassword =generatePassword();
        var Agent = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .CommercialRn(request.getCommercialRn())
                .patentNumber(request.getPatentNumber())
                .password(passwordEncoder.encode(generatedPassword))
                .role(Role.AGENT)
                .build();




        var savedAgent = repository.save(Agent);
        var jwtToken = jwtService.generateToken(Agent);

        saveUserToken(savedAgent, jwtToken);
        TextMessage message = new TextMessage(BRAND_NAME, request.getPhoneNumber(), "Your password is : " + generatedPassword);
        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(message);
        return RegisterAgentResponse.builder().message("success"+generatedPassword).build();
    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = (User) this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public List<User> findAll() {
        List<User> users = repository.findAllAgentWithRoleClient();
        return users;
    }

    public User findById(Long id) {
        User agent = repository.findAgentByClientId(id);
        return  agent;
    }






  public RegisterAgentResponse updateAgent(Long id , AgentRequest userUpdateRequest) {
        User agent=
               repository.findAgentByClientId(id);
                       if(agent!=null) {

                     agent.setFirstName(userUpdateRequest.getFirstName());
                     agent.setLastName(userUpdateRequest.getLastName());
                     agent.setEmail(userUpdateRequest.getEmail());
                     agent.setAddress(userUpdateRequest.getAddress());
                     agent.setPhoneNumber(userUpdateRequest.getPhoneNumber());
                     agent.setCommercialRn(userUpdateRequest.getCommercialRn());
                     agent.setPatentNumber(userUpdateRequest.getPatentNumber());

                           repository.save(agent);
                       }else {
                           System.out.println("The Agent with the Id Given not exist in the database ");
                       }

            return RegisterAgentResponse.builder().message("Agent updated successfully").build();
        }

    public RegisterAgentResponse delete(Long id) {
        User agent = repository.findAgentByClientId(id);
        if(agent !=null){
            repository.delete(agent);
            return RegisterAgentResponse.builder().message("Deleted with Success").build();
        }else {
            return RegisterAgentResponse.builder().message("Error during Deleting").build();

        }}

}

