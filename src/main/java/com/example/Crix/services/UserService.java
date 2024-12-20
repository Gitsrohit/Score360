package com.example.Crix.services;

import com.example.Crix.payloads.*;

import java.util.List;

public interface UserService {
    UserDto registerNewUser(RegisterRequest registerRequest);


    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userId);

    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    void deleteUser(Integer userId);
}
