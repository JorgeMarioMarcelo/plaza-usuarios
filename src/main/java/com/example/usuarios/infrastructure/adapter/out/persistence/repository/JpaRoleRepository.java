package com.example.usuarios.infrastructure.adapter.out.persistence.repository;

import com.example.usuarios.infrastructure.adapter.out.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByNombreIgnoreCase(String nombre);
}

