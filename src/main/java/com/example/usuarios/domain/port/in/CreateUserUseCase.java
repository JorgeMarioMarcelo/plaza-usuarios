package com.example.usuarios.domain.port.in;


import com.example.usuarios.domain.model.User;

public interface CreateUserUseCase {
    User create(User user);
}