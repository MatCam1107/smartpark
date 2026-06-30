package com.smartpark.ms_admin.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos retornados de un administrador")
public class AdminResponseDTO {

    @Schema(description = "ID único del administrador", example = "1")
    private Long idAdmin;

    @Schema(description = "Nombre del administrador", example = "Carlos Soto")
    private String nombre;

    @Schema(description = "Correo electrónico del administrador", example = "carlos@smartpark.cl")
    private String email;

    @Schema(description = "Acción realizada por el administrador", example = "BLOQUEO_USUARIO")
    private String accion;

    @Schema(description = "Fecha y hora en que se realizó la acción", example = "2025-06-10T10:00:00")
    private LocalDateTime fechaAccion;

    @Schema(description = "ID del usuario asociado a la acción", example = "3")
    private Long usuarioId;
}