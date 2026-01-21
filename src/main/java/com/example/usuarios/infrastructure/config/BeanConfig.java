package com.example.usuarios.infrastructure.config;

import com.example.usuarios.domain.usecase.GetUserService;
import com.example.usuarios.domain.usecase.ValidateRoleService;
import com.example.usuarios.domain.api.GetUserUseCase;
import com.example.usuarios.domain.api.ValidateRoleUseCase;
import com.example.usuarios.domain.spi.UserRepositoryPort;
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

