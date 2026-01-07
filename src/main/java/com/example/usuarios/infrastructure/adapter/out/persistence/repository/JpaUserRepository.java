package com.example.usuarios.infrastructure.adapter.out.persistence.repository;

import com.example.usuarios.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    @Query("""
        select u
        from UserEntity u
        join fetch u.rol
        where u.correo = :correo
    """)
    Optional<UserEntity> findByCorreoWithRol(@Param("correo") String correo);
    boolean existsByCorreo(String correo);
    boolean existsByNumeroDocumento(String numeroDocumento);
}

