package com.example.usuarios.infrastructure.adapter.in.web;

import com.example.usuarios.application.dto.request.CreateUserRequest;
import com.example.usuarios.application.dto.response.UserResponse;
import com.example.usuarios.domain.model.User;
import com.example.usuarios.domain.port.in.CreateUserUseCase;
import com.example.usuarios.domain.port.in.GetUserUseCase;
import com.example.usuarios.domain.port.in.ValidateRoleUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios del sistema")
public class UserController {

    private final CreateUserUseCase createUser;
    private final GetUserUseCase getUser;
    private final ValidateRoleUseCase validateRole;

    public UserController(CreateUserUseCase createUser, GetUserUseCase getUser, ValidateRoleUseCase validateRole) {
        this.createUser = createUser;
        this.getUser = getUser;
        this.validateRole = validateRole;
    }

    @Operation(
            summary = "Crear usuario",
            description = "Crea un usuario en el sistema. La clave se almacena encriptada (BCrypt) y el usuario queda activo por defecto."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos (validaciones de request)"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Correo o número de documento ya existe"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Rol no encontrado (si aplica)"
    )
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid CreateUserRequest req) {
        User u = new User();
        u.setNombre(req.getNombre());
        u.setApellido(req.getApellido());
        u.setNumeroDocumento(req.getNumeroDocumento());
        u.setCelular(req.getCelular());
        u.setFechaNacimiento(req.getFechaNacimiento());
        u.setCorreo(req.getCorreo());
        u.setClave(req.getClave());
        u.setActivo(true);

        User saved = createUser.create(u);
        return toResponse(saved);
    }

    @Operation(
            summary = "Consultar usuario por id",
            description = "Obtiene la información de un usuario por su identificador."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
    )
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return toResponse(getUser.getById(id));
    }

    @Operation(
            summary = "Validar si el usuario tiene un rol",
            description = "Retorna true si el usuario posee el rol indicado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Validación realizada",
            content = @Content(schema = @Schema(implementation = Boolean.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
    )
    @GetMapping("/{id}/has-role/{role}")
    public boolean hasRole(@PathVariable Long id, @PathVariable String role) {
        return validateRole.userHasRole(id, role);
    }

    private UserResponse toResponse(User u) {
        return new UserResponse(
                u.getId(), u.getNombre(), u.getApellido(), u.getNumeroDocumento(),
                u.getCelular(), u.getFechaNacimiento(), u.getCorreo(),
                u.getRol() != null ? u.getRol().getNombre() : null,
                u.getActivo()
        );
    }
}

