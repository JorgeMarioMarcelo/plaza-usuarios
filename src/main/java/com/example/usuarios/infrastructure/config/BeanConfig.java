package com.example.usuarios.infrastructure.config;

import com.example.usuarios.application.service.CreateUserService;
import com.example.usuarios.application.service.GetUserService;
import com.example.usuarios.application.service.ValidateRoleService;
import com.example.usuarios.domain.port.in.CreateUserUseCase;
import com.example.usuarios.domain.port.in.GetUserUseCase;
import com.example.usuarios.domain.port.in.ValidateRoleUseCase;
import com.example.usuarios.domain.port.out.RoleRepositoryPort;
import com.example.usuarios.domain.port.out.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public GetUserUseCase getUserUseCase(UserRepositoryPort userRepo) {
        return new GetUserService(userRepo);
    }

    @Bean
    public ValidateRoleUseCase validateRoleUseCase(UserRepositoryPort userRepo) {
        return new ValidateRoleService(userRepo);
    }
}

