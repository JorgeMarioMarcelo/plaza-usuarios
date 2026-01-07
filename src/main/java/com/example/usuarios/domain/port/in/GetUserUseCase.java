package com.example.usuarios.domain.port.in;

import com.example.usuarios.domain.model.User;

public interface GetUserUseCase {
    User getById(Long id);
}
