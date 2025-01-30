package com.openclassrooms.ycyw.services.interfaces;

import org.springframework.validation.BindingResult;

import com.openclassrooms.ycyw.dto.LoginDTO;
import com.openclassrooms.ycyw.dto.MessageDTO;
import com.openclassrooms.ycyw.dto.RegisterDTO;

public interface ValidationService {
    void validateRegister(RegisterDTO registerDTO);
    void validateLogin(LoginDTO loginDTO);
    void validateMessage(MessageDTO messageDTO, BindingResult bindingResult);
}