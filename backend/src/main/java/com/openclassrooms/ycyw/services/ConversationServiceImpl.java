package com.openclassrooms.ycyw.services;

import java.security.Principal;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.ConversationDTO;
import com.openclassrooms.ycyw.entities.Conversation;
import com.openclassrooms.ycyw.repositories.AuthenticationRepository;
import com.openclassrooms.ycyw.repositories.ConversationRepository;
import com.openclassrooms.ycyw.services.interfaces.ConversationService;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ModelMapper modelMapper;

    public ConversationServiceImpl(ConversationRepository conversationRepository, AuthenticationRepository authenticationRepository, ModelMapper modelMapper) {
        this.conversationRepository = conversationRepository;
        this.authenticationRepository = authenticationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<String> createConversationBetweenUsers(ConversationDTO conversationDTO, Principal principalUser, Long receiverId) {
        Conversation conversation = modelMapper.map(conversationDTO, Conversation.class);

        authenticationRepository.findByEmail(principalUser.getName()).ifPresent(conversation::setUser1Id);
        authenticationRepository.findById(receiverId).ifPresent(conversation::setUser2Id);

        conversationRepository.save(conversation);
        return Optional.of("Conversation created successfully");
    }
}
