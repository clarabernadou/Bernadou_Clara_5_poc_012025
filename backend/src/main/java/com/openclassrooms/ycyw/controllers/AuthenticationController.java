package com.openclassrooms.ycyw.controllers;

import javax.validation.Valid;

import com.openclassrooms.ycyw.dto.LoginDTO;
import com.openclassrooms.ycyw.dto.RegisterDTO;
import com.openclassrooms.ycyw.models.TokenResponse;
import com.openclassrooms.ycyw.services.interfaces.AuthenticationService;
import com.openclassrooms.ycyw.services.interfaces.ValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ValidationService validationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, ValidationService validationService) {
        this.authenticationService = authenticationService;
        this.validationService = validationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        validationService.validateRegister(registerDTO);
        return new TokenResponse(authenticationService.registerUser(registerDTO).orElseThrow(() -> new RuntimeException("Registration failed")));
    }

    @PostMapping("/login")
    public TokenResponse loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        validationService.validateLogin(loginDTO);
        return new TokenResponse(authenticationService.loginUser(loginDTO).orElseThrow(() -> new RuntimeException("Login failed")));
    }
}