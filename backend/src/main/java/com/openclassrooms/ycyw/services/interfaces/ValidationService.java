package com.openclassrooms.ycyw.services.interfaces;

import com.openclassrooms.ycyw.dto.LoginDTO;
import com.openclassrooms.ycyw.dto.RegisterDTO;

public interface ValidationService {
    void validateRegister(RegisterDTO registerDTO);
    void validateLogin(LoginDTO loginDTO);

}