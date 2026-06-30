package com.smartpark.ms_usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos necesarios para crear o actualizar un usuario")
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Schema(description = "Apellido del usuario", example = "Perez")
    private String apellido;

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    @Schema(description = "Correo electronico del usuario", example = "juan@smartpark.cl")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Schema(description = "Telefono de contacto", example = "+56912345678")
    private String telefono;

    @NotBlank(message = "El rol es obligatorio")
    @Schema(description = "Rol del usuario en la plataforma", example = "CLIENTE")
    private String rol;

    @NotNull(message = "El estado es obligatorio")
    @Schema(description = "Indica si el usuario esta activo", example = "true")
    private Boolean estado;
}