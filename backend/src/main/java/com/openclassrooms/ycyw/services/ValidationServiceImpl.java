package com.openclassrooms.ycyw.services;

import com.openclassrooms.ycyw.dto.LoginDTO;
import com.openclassrooms.ycyw.dto.RegisterDTO;
import com.openclassrooms.ycyw.services.interfaces.ValidationService;

import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import javax.validation.ValidationException;
import java.util.stream.Collectors;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;

    public ValidationServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void validateRegister(RegisterDTO registerDTO) {
        BindingResult bindingResult = new BeanPropertyBindingResult(registerDTO, "registerDTO");
        validator.validate(registerDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errors);
        }
    }

    @Override
    public void validateLogin(LoginDTO loginDTO) {
        BindingResult bindingResult = new BeanPropertyBindingResult(loginDTO, "loginDTO");
        validator.validate(loginDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errors);
        }
    }
}