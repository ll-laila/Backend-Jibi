package com.projet.demo.services;

import com.projet.demo.config.JwtService;
import com.projet.demo.entity.Client;
import com.projet.demo.entity.Role;
import com.projet.demo.model.ClientRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.repository.ClientRepository;
import com.projet.demo.token.Token;
import com.projet.demo.token.TokenRepository;
import com.projet.demo.token.TokenType;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final ClientRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final String CHARACTERS = "0123456789";
    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }
    public String formatPhoneNumber(String phoneNumber) {

        String formatted = phoneNumber.substring(1);
        return "212" + formatted;
    }


    public RegisterAgentResponse registerClient(ClientRequest request) {

        if (repository.existsByPhoneNumber(request.getPhoneNumber()) && repository.existsByEmail(request.getEmail() ) ) {
            throw new RuntimeException("Email Or Phone already exists");
        }

        String generatedPassword =generatePassword();
        var Clinet = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(generatedPassword))
                .isFirstLogin(true)
                .role(Role.CLIENT)
                .isPaymentAccountActivated(false)
                .createdDate(LocalDate.now())
                .build();
        var savedAgent = repository.save(Clinet);
        var jwtToken = jwtService.generateToken(Clinet);

        String formattedPhoneNumber=formatPhoneNumber(request.getPhoneNumber());
        VonageClient client = VonageClient.builder().apiKey("2053ed34").apiSecret("j2Cy3qjnDhKlnCbi").build();
        System.out.println(client);
        TextMessage message = new TextMessage("Jibi LKLCF",
                formattedPhoneNumber,
                "Bonjour "+ request.getFirstName() + ", votre mot de passe est "+ generatedPassword + "."
        );
        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
        }

        saveUserToken(savedAgent, jwtToken);
        return RegisterAgentResponse.builder().message("success " + generatedPassword).build();
    }


    private void saveUserToken(Client user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public RegisterAgentResponse updateClient(Long id , ClientRequest clientRequest) {
        Client client=
                repository.findClientByClientId(id);
                     if(client!=null) {
                         client.setFirstName(clientRequest.getFirstName());
                         client.setLastName(clientRequest.getLastName());
                         client.setEmail(clientRequest.getEmail());
                         client.setAddress(clientRequest.getAddress());
                         client.setCIN(clientRequest.getCIN());
                         client.setPhoneNumber(clientRequest.getPhoneNumber());


                         repository.save(client);
                     }else {System.out.println("The Client with the given Id not exist in the database");}
        return RegisterAgentResponse.builder().message("Agent updated successfully").build();
    }


    public List<Client> findAll() {
        return repository.findAllClientsWithRoleClient();
    }
    public Client findById(Long id) {
        return repository.findAgentByClientId(id);
    }

    public RegisterAgentResponse deleteClient(Long id) {
        Client client = repository.findClientByClientId(id);
        if(client !=null){
            repository.delete(client);
            return   RegisterAgentResponse.builder().message("Deleted with Success").build();
        }else {

              return RegisterAgentResponse.builder().message("Error during Deleting").build();
        }
    }
}
