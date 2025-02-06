package com.openclassrooms.ycyw.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.entities.Auth;
import com.openclassrooms.ycyw.repositories.UserRepository;
import com.openclassrooms.ycyw.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Auth> getAllUsers() {
        return userRepository.findAll();
    }
}
