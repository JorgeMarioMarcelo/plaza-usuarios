package com.example.usuarios.infrastructure.adapter.out.persistence;

import com.example.usuarios.domain.model.Role;
import com.example.usuarios.domain.spi.RoleRepositoryPort;
import com.example.usuarios.infrastructure.adapter.out.persistence.mapper.RoleMapper;
import com.example.usuarios.infrastructure.adapter.out.persistence.repository.JpaRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RolePersistenceAdapter implements RoleRepositoryPort {

    private final JpaRoleRepository jpa;
    private final RoleMapper mapper = new RoleMapper();

    public RolePersistenceAdapter(JpaRoleRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Role> findByNombreIgnoreCase(String nombre) {
        return jpa.findByNombreIgnoreCase(nombre).map(mapper::toDomain);
    }
}

