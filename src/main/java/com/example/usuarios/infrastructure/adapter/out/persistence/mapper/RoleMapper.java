package com.example.usuarios.infrastructure.adapter.out.persistence.mapper;

import com.example.usuarios.domain.model.Role;
import com.example.usuarios.infrastructure.adapter.out.persistence.entity.RoleEntity;

public class RoleMapper {
    public Role toDomain(RoleEntity e) {
        if (e == null) return null;
        return new Role(e.getId(), e.getNombre(), e.getDescripcion());
    }
    public RoleEntity toEntity(Role d) {
        if (d == null) return null;
        return RoleEntity.builder()
                .id(d.getId())
                .nombre(d.getNombre())
                .descripcion(d.getDescripcion())
                .build();
    }
}

