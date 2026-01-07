package com.example.usuarios.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank private String nombre;
    @NotBlank private String apellido;
    @NotBlank @Pattern(regexp = "^[0-9]+$", message = "Documento debe ser numérico") private String numeroDocumento;
    @NotBlank @Size(max = 13, message = "Celular máximo 13 caracteres")
    @Pattern(regexp = "^\\+?[0-9]{1,13}$", message = "Celular inválido. Ej: +573005698325") private String celular;
    @NotNull  private LocalDate fechaNacimiento;
    @NotBlank @Email(message = "Correo inválido") private String correo;
    @NotBlank @Size(min = 8, max = 72) private String clave;
}

