package com.openclassrooms.ycyw.services;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.MessageDTO;
import com.openclassrooms.ycyw.entities.Message;
import com.openclassrooms.ycyw.repositories.AuthenticationRepository;
import com.openclassrooms.ycyw.repositories.ConversationRepository;
import com.openclassrooms.ycyw.repositories.MessageRepository;
import com.openclassrooms.ycyw.services.interfaces.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ConversationRepository conversationRepository;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepository, AuthenticationRepository authenticationRepository, ConversationRepository conversationRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.authenticationRepository = authenticationRepository;
        this.conversationRepository = conversationRepository;
        this.modelMapper = modelMapper;

        PropertyMap<MessageDTO, Message> commentMap = new PropertyMap<MessageDTO, Message>() {
            @Override
            protected void configure() {
                map().setContent(source.getContent());
            }
        };

        this.modelMapper.addMappings(commentMap);
    }

    @Override
    public Optional<String> sendMessage(MessageDTO messageDTO, Long conversationId, Principal principalUser) {
        Message message = modelMapper.map(messageDTO, Message.class);

        authenticationRepository.findByEmail(principalUser.getName()).ifPresent(message::setUserId);
        conversationRepository.findById(conversationId).ifPresent(message::setConversationId);

        messageRepository.save(message);
        return Optional.of("Message sent successfully");
    }

    @Override
    public List<Message> getAllMessagesByConversationId(Long conversationId) {
        return messageRepository.findAll().stream()
            .filter(message -> message.getConversationId().getId().equals(conversationId))
            .sorted(Comparator.comparing(Message::getCreatedAt))
            .collect(Collectors.toList());
    }
}
