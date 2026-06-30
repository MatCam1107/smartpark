package com.smartpark.ms_usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representacion de un usuario devuelto por la API")
public class UsuarioResponseDTO {

    @Schema(description = "Identificador unico del usuario", example = "1")
    private Long idUsuario;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Perez")
    private String apellido;

    @Schema(description = "Correo electronico del usuario", example = "juan@smartpark.cl")
    private String email;

    @Schema(description = "Telefono de contacto", example = "+56912345678")
    private String telefono;

    @Schema(description = "Rol del usuario en la plataforma", example = "CLIENTE")
    private String rol;

    @Schema(description = "Indica si el usuario esta activo", example = "true")
    private Boolean estado;
}