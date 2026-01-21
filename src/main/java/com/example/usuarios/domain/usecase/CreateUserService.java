package com.example.usuarios.domain.usecase;

import com.example.usuarios.domain.exception.BusinessException;
import com.example.usuarios.domain.exception.NotFoundException;
import com.example.usuarios.domain.model.Role;
import com.example.usuarios.domain.model.User;
import com.example.usuarios.domain.api.CreateUserUseCase;
import com.example.usuarios.domain.spi.RoleRepositoryPort;
import com.example.usuarios.domain.spi.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepositoryPort userRepo;
    private final RoleRepositoryPort roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        if (userRepo.existsByCorreo(user.getCorreo())) {
            throw new BusinessException("El correo ya existe");
        }
        if (userRepo.existsByNumeroDocumento(user.getNumeroDocumento())) {
            throw new BusinessException("El número de documento ya existe");
        }

        int edad = Period.between(user.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 18) throw new BusinessException("El usuario debe ser mayor de edad");

        Role propietario = roleRepo.findByNombreIgnoreCase("PROPIETARIO")
                .orElseThrow(() -> new NotFoundException("Rol PROPIETARIO no existe"));

        user.setRol(propietario);
        if (user.getActivo() == null) user.setActivo(true);
        user.setClave(passwordEncoder.encode(user.getClave()));

        return userRepo.save(user);
    }

}
