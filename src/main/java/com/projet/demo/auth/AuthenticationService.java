package com.projet.demo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.demo.config.JwtService;
import com.projet.demo.model.User;
import com.projet.demo.model.Role;
import com.projet.demo.model.RegisterRequest;
import com.projet.demo.repository.UserRepo;
import com.projet.demo.model.Token;
import com.projet.demo.repository.TokenRepo;
import com.projet.demo.model.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepo repository;
  private final TokenRepo tokenRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    if (repository.existsByPhoneNumber(request.getPhoneNumber())) {
      throw new RuntimeException("Phone Number already exists");
    }
    var user = User.builder()
        .firstName(request.getFirstname())
        .lastName(request.getLastname())
        .phoneNumber(request.getPhoneNumber())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.ADMIN)
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getPhoneNumber(),
            request.getPassword()
        )
    );
    UserDetails user = (UserDetails) repository.findByPhoneNumber(request.getPhoneNumber());
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);



    revokeAllUserTokens((User) user);
    saveUserToken((User) user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
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
    var validUserTokens = tokenRepo.findAllValidTokenByUser((long) Math.toIntExact(user.getId()));
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
    final String userPhoneNumber;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userPhoneNumber = jwtService.extractUsername(refreshToken);
    if (userPhoneNumber != null) {
      var user = this.repository.findByPhoneNumber(userPhoneNumber);
      if (jwtService.isTokenValid(refreshToken, (UserDetails) user)) {
        var accessToken = jwtService.generateToken((UserDetails) user);
        revokeAllUserTokens((User) user);
        saveUserToken((User) user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
