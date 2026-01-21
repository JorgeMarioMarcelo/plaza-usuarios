package com.example.usuarios.domain.api;

public interface ValidateRoleUseCase {
    boolean userHasRole(Long userId, String roleName);
}
