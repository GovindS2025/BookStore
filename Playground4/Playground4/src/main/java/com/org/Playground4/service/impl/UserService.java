package com.org.Playground4.service.impl;

import com.org.Playground4.dto.UserRequest;
import com.org.Playground4.dto.UserResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse login(@Valid UserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
}
