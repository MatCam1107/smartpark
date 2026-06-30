package com.smartpark.ms_auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para registrar un nuevo usuario")
public class RegisterRequestDTO {

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan@smartpark.cl")
    @Email(message = "El email debe tener formato valido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "password123")
    @NotBlank(message = "La password es obligatoria")
    private String password;

    @Schema(description = "Rol asignado al usuario", example = "CLIENTE")
    @NotBlank(message = "El rol es obligatorio")
    private String rol;
}