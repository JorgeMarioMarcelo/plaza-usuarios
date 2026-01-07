package com.example.usuarios.domain.port.in;

public interface ValidateRoleUseCase {
    boolean userHasRole(Long userId, String roleName);
}
