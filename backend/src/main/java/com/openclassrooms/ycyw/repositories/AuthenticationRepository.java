package com.openclassrooms.ycyw.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.ycyw.entities.Auth;

@Repository
public interface AuthenticationRepository extends CrudRepository<Auth, Long> {
    Optional<Auth> findByEmail(String email);
}
