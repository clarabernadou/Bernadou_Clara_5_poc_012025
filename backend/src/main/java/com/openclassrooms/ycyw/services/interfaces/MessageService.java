package com.openclassrooms.ycyw.services.interfaces;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.ycyw.dto.MessageDTO;
import com.openclassrooms.ycyw.entities.Message;

public interface MessageService {
    Optional<String> sendMessage(MessageDTO messageDTO, Long conversationId, Principal principalUser);
    List<Message> getAllMessagesByConversationId(Long conversationId);
}
