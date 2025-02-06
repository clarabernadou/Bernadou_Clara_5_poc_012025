package com.openclassrooms.ycyw.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.ycyw.dto.MessageDTO;
import com.openclassrooms.ycyw.entities.Message;
import com.openclassrooms.ycyw.services.interfaces.MessageService;

@RestController
@RequestMapping("/api/auth/conversation")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/{conversationId}/message")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageDTO messageDTO, @PathVariable Long conversationId, Principal principalUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.sendMessage(messageDTO, conversationId, principalUser));
    }

    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesOfConversation(@PathVariable Long conversationId) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessagesByConversationId(conversationId));
    }
}
