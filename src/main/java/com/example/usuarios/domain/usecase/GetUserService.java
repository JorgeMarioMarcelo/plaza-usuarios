package com.example.usuarios.domain.usecase;

import com.example.usuarios.domain.exception.NotFoundException;
import com.example.usuarios.domain.model.User;
import com.example.usuarios.domain.api.GetUserUseCase;
import com.example.usuarios.domain.spi.UserRepositoryPort;

public class GetUserService implements GetUserUseCase {
    private final UserRepositoryPort userRepo;

    public GetUserService(UserRepositoryPort userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }
}
