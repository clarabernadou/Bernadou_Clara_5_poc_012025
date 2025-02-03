package com.openclassrooms.ycyw.services.interfaces;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.ycyw.dto.ConversationDTO;
import com.openclassrooms.ycyw.entities.Conversation;

public interface ConversationService {
    Optional<String> createConversationBetweenUsers(ConversationDTO conversationDTO, Principal user1Id, Long user2Id);
    List<Conversation> getAllUserConversations(Principal principal);
}
