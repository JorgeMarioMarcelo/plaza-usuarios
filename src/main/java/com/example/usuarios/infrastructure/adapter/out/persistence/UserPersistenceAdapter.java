package com.example.usuarios.infrastructure.adapter.out.persistence;

import com.example.usuarios.domain.model.User;
import com.example.usuarios.domain.port.out.UserRepositoryPort;
import com.example.usuarios.infrastructure.adapter.out.persistence.mapper.UserMapper;
import com.example.usuarios.infrastructure.adapter.out.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpa;
    private final UserMapper mapper = new UserMapper();

    public UserPersistenceAdapter(JpaUserRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(jpa.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByCorreo(String correo) {
        return jpa.findByCorreoWithRol(correo).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return jpa.existsByCorreo(correo);
    }

    @Override
    public boolean existsByNumeroDocumento(String numeroDocumento) {
        return jpa.existsByNumeroDocumento(numeroDocumento);
    }
}

