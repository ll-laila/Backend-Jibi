package com.projet.demo.services;

import com.projet.demo.config.JwtService;
import com.projet.demo.entity.BankAccount;
import com.projet.demo.entity.Client;
import com.projet.demo.entity.PaymentAccount;
import com.projet.demo.entity.Role;
import com.projet.demo.model.AgentRequest;
import com.projet.demo.model.ClientRequest;
import com.projet.demo.model.PaymentAccountRequest;
import com.projet.demo.model.RegisterAgentResponse;
import com.projet.demo.repository.BankAccountRepository;
import com.projet.demo.repository.ClientRepository;
import com.projet.demo.repository.PaymentAccountRepository;
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
    private final PaymentAccountRepository paymentRepository;
    private final BankAccountRepository bankRepository;
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

    public RegisterAgentResponse registerClient(ClientRequest request,PaymentAccountRequest paymentAccountRequest) {
        if (repository.existsByPhoneNumber(request.getPhoneNumber()) && repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Phone num already exists Or Email");
        }
        BankAccount bankAccount = BankAccount.builder()
                .balance(0.0)
                .build();
        bankRepository.save(bankAccount);

        var paymentAccount = PaymentAccount.builder()
                .type(paymentAccountRequest.getType())
                .accountBalance(0)
                .build();

        paymentRepository.save(paymentAccount);
        String generatedPassword =generatePassword();
        var Client1 = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(generatedPassword))
                .role(Role.CLIENT)
                .build();
        Client1.setPaymentAccount(paymentAccount);
        Client1.setBankAccount(bankAccount);

        String formattedPhoneNumber=formatPhoneNumber(request.getPhoneNumber());
        System.out.println(formattedPhoneNumber);
        var savedClient = repository.save(Client1);
        var jwtToken = jwtService.generateToken(Client1);

        saveUserToken(savedClient, jwtToken);

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
        return RegisterAgentResponse.builder().message("success"+generatedPassword).build();
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
