package com.example.usuarios.infrastructure.config;

import com.example.usuarios.domain.model.User;
import com.example.usuarios.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepo;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        User user = userRepo.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String roleName = user.getRol() != null ? user.getRol().getNombre() : "CLIENTE";
        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + roleName));

        return new org.springframework.security.core.userdetails.User(
                user.getCorreo(),
                user.getClave(),
                Boolean.TRUE.equals(user.getActivo()),
                true, true, true,
                authorities
        );
    }
}
