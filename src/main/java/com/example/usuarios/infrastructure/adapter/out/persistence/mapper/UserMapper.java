package com.example.usuarios.infrastructure.adapter.out.persistence.mapper;

import com.example.usuarios.domain.model.User;
import com.example.usuarios.infrastructure.adapter.out.persistence.entity.UserEntity;

public class UserMapper {

    private final RoleMapper roleMapper = new RoleMapper();

    public User toDomain(UserEntity e) {
        if (e == null) return null;
        User d = new User();
        d.setId(e.getId());
        d.setNombre(e.getNombre());
        d.setApellido(e.getApellido());
        d.setNumeroDocumento(e.getNumeroDocumento());
        d.setCelular(e.getCelular());
        d.setFechaNacimiento(e.getFechaNacimiento());
        d.setCorreo(e.getCorreo());
        d.setClave(e.getClave());
        d.setRol(roleMapper.toDomain(e.getRol()));
        d.setActivo(e.getActivo());
        return d;
    }

    public UserEntity toEntity(User d) {
        if (d == null) return null;
        return UserEntity.builder()
                .id(d.getId())
                .nombre(d.getNombre())
                .apellido(d.getApellido())
                .numeroDocumento(d.getNumeroDocumento())
                .celular(d.getCelular())
                .fechaNacimiento(d.getFechaNacimiento())
                .correo(d.getCorreo())
                .clave(d.getClave())
                .rol(roleMapper.toEntity(d.getRol()))
                .activo(d.getActivo())
                .build();
    }
}

