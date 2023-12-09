package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.contract.requests.AuthenticateRequest;
import org.example.contract.requests.RegisterRequest;
import org.example.contract.responses.AuthenticationResponse;
import org.example.contract.responses.UserResponse;
import org.example.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService service;

    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        return new ResponseEntity<>(service.register(request), HttpStatus.OK);
    }

    @PostMapping(value = "/pub/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/pub/publicKey")
    public ResponseEntity<String> publicKey(){
        return ResponseEntity.ok(service.getPublicKey());
    }


}
