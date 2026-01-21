package com.example.usuarios.domain.api;


import com.example.usuarios.domain.model.User;

public interface CreateUserUseCase {
    User create(User user);
}