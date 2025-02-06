package com.openclassrooms.ycyw.services.interfaces;

import java.util.List;

import com.openclassrooms.ycyw.entities.Auth;

public interface UserService {
    List<Auth> getAllUsers();
}
