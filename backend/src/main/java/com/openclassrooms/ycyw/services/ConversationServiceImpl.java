package com.openclassrooms.ycyw.services;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<Conversation> getAllUserConversations(Principal principal) {
        Long userId = authenticationRepository.findByEmail(principal.getName()).get().getId();
        return StreamSupport.stream(conversationRepository.findAll().spliterator(), false)
            .filter(conversation -> conversation.getUser1Id().getId().equals(userId) || conversation.getUser2Id().getId().equals(userId))
            .sorted(Comparator.comparing(Conversation::getCreatedAt))
            .collect(Collectors.toList());
    }
}
