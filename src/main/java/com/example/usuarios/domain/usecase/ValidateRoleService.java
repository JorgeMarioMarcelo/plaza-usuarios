package com.example.usuarios.domain.usecase;

import com.example.usuarios.domain.api.ValidateRoleUseCase;
import com.example.usuarios.domain.spi.UserRepositoryPort;

public class ValidateRoleService implements ValidateRoleUseCase {
    private final UserRepositoryPort userRepo;

    public ValidateRoleService(UserRepositoryPort userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean userHasRole(Long userId, String roleName) {
        return userRepo.findById(userId)
                .map(u -> u.getRol() != null && roleName.equalsIgnoreCase(u.getRol().getNombre()))
                .orElse(false);
    }
}
