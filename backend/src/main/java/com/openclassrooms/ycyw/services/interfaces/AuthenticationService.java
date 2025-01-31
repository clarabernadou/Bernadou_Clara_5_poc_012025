package com.openclassrooms.ycyw.services.interfaces;

import java.util.Optional;

import com.openclassrooms.ycyw.dto.LoginDTO;
import com.openclassrooms.ycyw.dto.RegisterDTO;

public interface AuthenticationService {
    Optional<String> registerUser(RegisterDTO registerDTO);
    Optional<String> loginUser(LoginDTO loginDTO);
}
