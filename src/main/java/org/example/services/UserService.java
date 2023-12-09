package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.contract.requests.AuthenticateRequest;
import org.example.contract.requests.RegisterRequest;
import org.example.contract.responses.AuthenticationResponse;
import org.example.contract.responses.UserResponse;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;


    public UserResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        final User save = repository.save(user);
        final UserResponse userResponse = new UserResponse(save.getId(), save.getEmail(), save.getRole(), !save.isNonLocked());
        return userResponse;
    }


    public AuthenticationResponse authenticate(AuthenticateRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public String getPublicKey(){
        return jwtService.getPublicKey();
    }

    public Optional<User> getUser(String email){
        return repository.findByEmail(email);
    }
}
