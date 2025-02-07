package com.openclassrooms.ycyw.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.ycyw.dto.ConversationDTO;
import com.openclassrooms.ycyw.entities.Conversation;
import com.openclassrooms.ycyw.services.interfaces.ConversationService;

@RestController
@RequestMapping("/api/auth/user")
public class ConversationController {
    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("/{receiverId}/conversation")
    public ResponseEntity<?> createConversationWithUser(ConversationDTO conversationDTO, @PathVariable Long receiverId, Principal principalUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conversationService.createConversationBetweenUsers(conversationDTO, principalUser, receiverId));
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<Conversation>> getAllUserConversations(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(conversationService.getAllUserConversations(principal));
    }
}
