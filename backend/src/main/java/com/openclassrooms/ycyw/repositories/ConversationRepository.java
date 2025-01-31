package com.openclassrooms.ycyw.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.ycyw.entities.Conversation;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {
    Optional<Conversation> findById(Long id);
}
