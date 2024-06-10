package com.projet.demo.services;

import com.projet.demo.config.JwtService;
import com.projet.demo.entity.*;
import com.projet.demo.mapper.OperationMapper;
import com.projet.demo.model.*;
import com.projet.demo.repository.BankAccountRepository;
import com.projet.demo.repository.ClientRepository;
import com.projet.demo.repository.OperationRepository;
import com.projet.demo.repository.PaymentAccountRepository;
import com.projet.demo.token.Token;
import com.projet.demo.token.TokenRepository;
import com.projet.demo.token.TokenType;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.loadtime.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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

    public RegisterAgentResponse registerClient(ClientRequest request, PaymentAccountRequest paymentAccountRequest) {
        if (repository.existsByPhoneNumber(request.getPhoneNumber()) || repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Phone number or Email already exists");}
                BankAccount bankAccount = BankAccount.builder()
                        .balance(40000)
                        .build();
                bankRepository.save(bankAccount);

                var paymentAccount = PaymentAccount.builder()
                        .type(paymentAccountRequest.getType())
                        .accountBalance(0)
                        .build();
                paymentRepository.save(paymentAccount);

                String generatedPassword = generatePassword();
                var client = Client.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .phoneNumber(request.getPhoneNumber())
                        .password(passwordEncoder.encode(generatedPassword))
                        .role(Role.CLIENT)
                        .agentId(request.getAgentId()) // Définir l'agent ID ici
                        .build();
                client.setPaymentAccount(paymentAccount);
                client.setBankAccount(bankAccount);

                var savedClient = repository.save(client);
                var jwtToken = jwtService.generateToken(client);

                saveUserToken(savedClient, jwtToken);

                // Envoi de SMS via Vonage
                String formattedPhoneNumber = formatPhoneNumber(request.getPhoneNumber());
                TextMessage message = new TextMessage("Jibi LKLCF",
                        formattedPhoneNumber,
                        "Bonjour " + request.getFirstName() + ", votre mot de passe est " + generatedPassword + "."
                );
                SmsSubmissionResponse response = VonageClient.builder().apiKey("2053ed34").apiSecret("j2Cy3qjnDhKlnCbi").build().getSmsClient().submitMessage(message);
                if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
                    System.out.println("Message sent successfully.");
                } else {
                    System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
                }

                return RegisterAgentResponse.builder().message("Success: " + generatedPassword).build();
            }


            private void saveUserToken (Client user, String jwtToken){
                var token = Token.builder()
                        .user(user)
                        .token(jwtToken)
                        .tokenType(TokenType.BEARER)
                        .expired(false)
                        .revoked(false)
                        .build();
                tokenRepository.save(token);
            }

            public RegisterAgentResponse updateClient (Long id, ClientRequest clientRequest, PaymentAccountRequest
            paymentAccountRequest){
                Client client = repository.findClientByClientId(id);
                if (client != null) {
                    // Mettre à jour les informations du client
                    client.setFirstName(clientRequest.getFirstName());
                    client.setLastName(clientRequest.getLastName());
                    client.setEmail(clientRequest.getEmail());
                    client.setPhoneNumber(clientRequest.getPhoneNumber());

                    // Mettre à jour les informations du compte de paiement
                    PaymentAccount paymentAccount = client.getPaymentAccount();
                    paymentAccount.setType(paymentAccountRequest.getType()); // Mettre à jour le type du compte de paiement

                    // Enregistrer les modifications
                    repository.save(client);

                    return RegisterAgentResponse.builder().message("Agent updated successfully").build();
                } else {
                    System.out.println("The Client with the given Id does not exist in the database");
                    return RegisterAgentResponse.builder().message("Error: Client not found").build();
                }
            }


            public List<Client> findAll () {
                return repository.findAllClientsWithRoleClient();
            }

            public List<Client> getClientsByAgentId (Long agentId){
                return repository.findByAgentId(agentId);
            }

            public Client findById (Long id){
                return repository.findAgentByClientId(id);
            }

            public RegisterAgentResponse deleteClient (Long id){
                Client client = repository.findClientByClientId(id);
                if (client != null) {
                    repository.delete(client);
                    return RegisterAgentResponse.builder().message("Deleted with Success").build();
                } else {

                    return RegisterAgentResponse.builder().message("Error during Deleting").build();
                }
            }

        @Autowired
        private OperationRepository operationRepository;
        public List<OperationResponse> getAgentOperation(Long id) {
            List<Operation> operations = operationRepository.findOperationsByAgentId(id);
            return operations.stream()
                    .map(OperationMapper::ConvertToDto)
                    .collect(Collectors.toList());
        }

        }

