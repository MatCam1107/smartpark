package com.smartpark.ms_auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta retornada tras un registro o inicio de sesión")
public class AuthResponseDTO {

    @Schema(description = "ID único del usuario autenticado", example = "1")
    private Long idAuth;

    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan@smartpark.cl")
    private String email;

    @Schema(description = "Rol del usuario en el sistema", example = "CLIENTE")
    private String rol;

    @Schema(description = "Mensaje de respuesta del sistema", example = "Login exitoso")
    private String mensaje;
}