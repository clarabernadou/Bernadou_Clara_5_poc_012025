package com.openclassrooms.ycyw.services.interfaces;

import java.security.Principal;
import java.util.Optional;

import com.openclassrooms.ycyw.dto.MessageDTO;

public interface MessageService {
    Optional<String> sendMessage(MessageDTO messageDTO, Long receiverId, Principal principalUser);
}
