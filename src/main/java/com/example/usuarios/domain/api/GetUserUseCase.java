package com.example.usuarios.domain.api;

import com.example.usuarios.domain.model.User;

public interface GetUserUseCase {
    User getById(Long id);
}
