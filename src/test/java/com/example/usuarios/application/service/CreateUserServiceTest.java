package com.example.usuarios.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.example.usuarios.domain.usecase.CreateUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.usuarios.domain.exception.BusinessException;
import com.example.usuarios.domain.exception.NotFoundException;
import com.example.usuarios.domain.model.Role;
import com.example.usuarios.domain.model.User;
import com.example.usuarios.infrastructure.adapter.out.persistence.RolePersistenceAdapter;
import com.example.usuarios.infrastructure.adapter.out.persistence.UserPersistenceAdapter;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock private UserPersistenceAdapter userRepo;
    @Mock private RolePersistenceAdapter roleRepo;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private CreateUserService service;

    @Test
    void create_ok_asignaRolPropietario_y_encriptaClave() {
        // Arrange
        User user = new User();
        user.setNombre("Mario");
        user.setApellido("Reina");
        user.setNumeroDocumento("1020838537");
        user.setCelular("+573105618505");
        user.setFechaNacimiento(LocalDate.now().minusYears(20));
        user.setCorreo("mario@test.com");
        user.setClave("123456");

        Role propietario = new Role();
        propietario.setId(1L);
        propietario.setNombre("PROPIETARIO");

        when(userRepo.existsByCorreo("mario@test.com")).thenReturn(false);
        when(userRepo.existsByNumeroDocumento("1020838537")).thenReturn(false);
        when(roleRepo.findByNombreIgnoreCase("PROPIETARIO")).thenReturn(Optional.of(propietario));
        when(passwordEncoder.encode("123456")).thenReturn("$2a$10$HASH");
        when(userRepo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        User saved = service.create(user);

        // Assert
        assertEquals("$2a$10$HASH", saved.getClave());
        assertEquals(Boolean.TRUE, saved.getActivo());
        assertNotNull(saved.getRol());
        assertEquals("PROPIETARIO", saved.getRol().getNombre());

        verify(passwordEncoder).encode("123456");
        verify(userRepo).save(any(User.class));
    }

    @Test
    void create_falla_si_correo_existe() {
        User user = new User();
        user.setCorreo("mario@test.com");
        user.setNumeroDocumento("1020838537");
        user.setFechaNacimiento(LocalDate.now().minusYears(20));

        when(userRepo.existsByCorreo("mario@test.com")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.create(user));
        assertEquals("El correo ya existe", ex.getMessage());

        verify(userRepo, never()).save(any());
    }

    @Test
    void create_activo_null_debe_quedar_true() {
        User user = new User();
        user.setCorreo("mario@test.com");
        user.setNumeroDocumento("1020838537");
        user.setFechaNacimiento(LocalDate.now().minusYears(20));
        user.setClave("123456");
        user.setActivo(null);

        Role propietario = new Role();
        propietario.setId(1L);
        propietario.setNombre("PROPIETARIO");

        when(userRepo.existsByCorreo(anyString())).thenReturn(false);
        when(userRepo.existsByNumeroDocumento(anyString())).thenReturn(false);
        when(roleRepo.findByNombreIgnoreCase("PROPIETARIO")).thenReturn(Optional.of(propietario));
        when(passwordEncoder.encode("123456")).thenReturn("$2a$10$HASH");
        when(userRepo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = service.create(user);

        assertEquals(Boolean.TRUE, saved.getActivo());

    }

    @Test
    void create_falla_si_documento_existe() {
        User user = new User();
        user.setCorreo("mario@test.com");
        user.setNumeroDocumento("1020838537");
        user.setFechaNacimiento(LocalDate.now().minusYears(20));

        when(userRepo.existsByCorreo(anyString())).thenReturn(false);
        when(userRepo.existsByNumeroDocumento("1020838537")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.create(user));
        assertEquals("El número de documento ya existe", ex.getMessage());

        verify(userRepo, never()).save(any());
    }

    @Test
    void create_falla_si_menor_de_edad() {
        User user = new User();
        user.setCorreo("mario@test.com");
        user.setNumeroDocumento("1020838537");
        user.setFechaNacimiento(LocalDate.now().minusYears(17));

        when(userRepo.existsByCorreo(anyString())).thenReturn(false);
        when(userRepo.existsByNumeroDocumento(anyString())).thenReturn(false);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.create(user));
        assertEquals("El usuario debe ser mayor de edad", ex.getMessage());

        verify(userRepo, never()).save(any());
    }

    @Test
    void create_ok_si_tiene_18_anos_exactos() {
        User user = new User();
        user.setCorreo("mario@test.com");
        user.setNumeroDocumento("1020838537");
        user.setFechaNacimiento(LocalDate.now().minusYears(18));
        user.setClave("123456");

        Role propietario = new Role();
        propietario.setId(1L);
        propietario.setNombre("PROPIETARIO");

        when(userRepo.existsByCorreo(anyString())).thenReturn(false);
        when(userRepo.existsByNumeroDocumento(anyString())).thenReturn(false);
        when(roleRepo.findByNombreIgnoreCase("PROPIETARIO")).thenReturn(Optional.of(propietario));
        when(passwordEncoder.encode("123456")).thenReturn("$2a$10$HASH");
        when(userRepo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = service.create(user);

        assertNotNull(saved);
    }

    @Test
    void create_falla_si_no_existe_rol_propietario() {
        User user = new User();
        user.setCorreo("mario@test.com");
        user.setNumeroDocumento("1020838537");
        user.setFechaNacimiento(LocalDate.now().minusYears(20));
        user.setClave("123456");

        when(userRepo.existsByCorreo(anyString())).thenReturn(false);
        when(userRepo.existsByNumeroDocumento(anyString())).thenReturn(false);
        when(roleRepo.findByNombreIgnoreCase("PROPIETARIO")).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.create(user));
        assertTrue(ex.getMessage().toLowerCase().contains("rol"));

        verify(userRepo, never()).save(any());
    }
}
