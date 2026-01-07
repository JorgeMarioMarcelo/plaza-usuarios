package com.example.usuarios.infrastructure.adapter.in.web;

import com.example.usuarios.application.dto.request.CreateUserRequest;
import com.example.usuarios.application.dto.response.UserResponse;
import com.example.usuarios.domain.model.Role;
import com.example.usuarios.domain.model.User;
import com.example.usuarios.domain.port.in.CreateUserUseCase;
import com.example.usuarios.domain.port.in.GetUserUseCase;
import com.example.usuarios.domain.port.in.ValidateRoleUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUser;
    private final GetUserUseCase getUser;
    private final ValidateRoleUseCase validateRole;

    public UserController(CreateUserUseCase createUser, GetUserUseCase getUser, ValidateRoleUseCase validateRole) {
        this.createUser = createUser;
        this.getUser = getUser;
        this.validateRole = validateRole;
    }

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

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return toResponse(getUser.getById(id));
    }

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

