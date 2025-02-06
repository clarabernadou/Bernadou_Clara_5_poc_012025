package com.openclassrooms.ycyw.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.ycyw.entities.Auth;

@Repository
public interface UserRepository extends CrudRepository<Auth, Long> {
    List<Auth> findAll();
}
