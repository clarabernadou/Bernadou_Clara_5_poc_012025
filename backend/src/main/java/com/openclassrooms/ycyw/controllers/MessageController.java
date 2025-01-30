package com.openclassrooms.ycyw.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.ycyw.dto.MessageDTO;
import com.openclassrooms.ycyw.models.MessageResponse;
import com.openclassrooms.ycyw.services.interfaces.MessageService;
import com.openclassrooms.ycyw.services.interfaces.ValidationService;

@RestController
@RequestMapping("/api/auth")
public class MessageController {

    private final MessageService messageService;
    private final ValidationService validationService;

    public MessageController(MessageService messageService, ValidationService validationService) {
        this.messageService = messageService;
        this.validationService = validationService;
    }

    @PostMapping("/user/{receiverId}")
    public ResponseEntity<MessageResponse> createComment(@Valid @RequestBody MessageDTO messageDTO, @PathVariable Long receiverId, Principal principalUser, BindingResult bindingResult) {
        validationService.validateMessage(messageDTO, bindingResult);
        return ResponseEntity.ok(new MessageResponse(messageService.sendMessage(messageDTO, receiverId, principalUser).get()));
    }
}
