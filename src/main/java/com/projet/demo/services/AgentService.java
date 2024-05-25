package com.projet.demo.services;

import com.projet.demo.config.JwtService;
import com.projet.demo.model.User;
import com.projet.demo.model.Role;
import com.projet.demo.model.ClientRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.repository.UserRepo;
import com.projet.demo.model.Token;
import com.projet.demo.repository.TokenRepo;
import com.projet.demo.model.TokenType;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AgentService {

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


    public RegisterAgentResponse registerClient(ClientRequest request) {

        if (repository.existsByPhoneNumber(request.getPhoneNumber()) && repository.existsByEmail(request.getEmail() ) ) {
            throw new RuntimeException("Email Or Phone already exists");
        }

        String generatedpassword = generatePassword();
        var Clinet = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(generatedpassword))
                .CIN(request.getCIN())
                .isFirstLogin(true)
                .role(Role.CLIENT)
                .isPaymentAccountActivated(false)
                .createdDate(LocalDate.now())
                .isPaymentAccountActivated(false)
                .birthDate(request.getBirthDate())
                .build();
        var savedAgent = repository.save(Clinet);
        var jwtToken = jwtService.generateToken(Clinet);
        //  var refreshToken = jwtService.generateRefreshToken(Agent);
        TextMessage message = new TextMessage(BRAND_NAME, request.getPhoneNumber(), "Your password is : " + generatedpassword);
        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(message);
        saveUserToken(savedAgent, jwtToken);
        return RegisterAgentResponse.builder().message("success " + generatedpassword).build();
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


    public RegisterAgentResponse updateClient(Long id , ClientRequest clientRequest) {
        User user =
                repository.findClientByClientId(id);
                     if(user !=null) {
                         user.setFirstName(clientRequest.getFirstName());
                         user.setLastName(clientRequest.getLastName());
                         user.setEmail(clientRequest.getEmail());
                         user.setAddress(clientRequest.getAddress());
                         user.setCIN(clientRequest.getCIN());
                         user.setPhoneNumber(clientRequest.getPhoneNumber());


                         repository.save(user);
                     }else {System.out.println("The User with the given Id not exist in the database");}
        return RegisterAgentResponse.builder().message("Agent updated successfully").build();
    }


    public List<User> findAll() {
        List<User> users = repository.findAllClientsWithRoleClient();
        return users;
    }
    public User findById(Long id) {
        User user = repository.findClientByClientId(id);
        return user;
    }

    public RegisterAgentResponse deleteClient(Long id) {
        User user = repository.findClientByClientId(id);
        if(user !=null){
            repository.delete(user);
            return   RegisterAgentResponse.builder().message("Deleted with Success").build();
        }else {

              return RegisterAgentResponse.builder().message("Error during Deleting").build();
        }
    }
}
