package com.example.usuarios.domain.port.out;

import com.example.usuarios.domain.model.Role;

import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findById(Long id);
    Optional<Role> findByNombreIgnoreCase(String nombre);
}
