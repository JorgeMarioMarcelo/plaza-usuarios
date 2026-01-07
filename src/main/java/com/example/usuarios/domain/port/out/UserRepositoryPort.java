package com.example.usuarios.domain.port.out;

import com.example.usuarios.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    boolean existsByNumeroDocumento(String numeroDocumento);
}
