package com.openclassrooms.ycyw.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.ycyw.entities.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
        List<Message> findAllById(Long id);
        List<Message> findAll();
}
