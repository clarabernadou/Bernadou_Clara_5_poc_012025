package com.openclassrooms.ycyw.services;

import java.security.Principal;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.MessageDTO;
import com.openclassrooms.ycyw.entities.Message;
import com.openclassrooms.ycyw.repositories.AuthenticationRepository;
import com.openclassrooms.ycyw.repositories.MessageRepository;
import com.openclassrooms.ycyw.services.interfaces.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final AuthenticationRepository authenticationRepository;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepository, AuthenticationRepository authenticationRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.authenticationRepository = authenticationRepository;
        this.modelMapper = modelMapper;

        PropertyMap<MessageDTO, Message> commentMap = new PropertyMap<MessageDTO, Message>() {
            @Override
            protected void configure() {
                map().setId(source.getSenderId());
            }
        };

        this.modelMapper.addMappings(commentMap);
    }

    @Override
    public Optional<String> sendMessage(MessageDTO messageDTO, Long receiverId, Principal principalUser) {
        Message message = modelMapper.map(messageDTO, Message.class);

        authenticationRepository.findByEmail(principalUser.getName()).ifPresent(message::setSenderId);
        authenticationRepository.findById(receiverId).ifPresent(message::setReceiverId);

        messageRepository.save(message);
        return Optional.of("Message sent successfully");
    }
}
