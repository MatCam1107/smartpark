package com.smartpark.ms_admin.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Datos requeridos para crear o actualizar un administrador")
public class AdminRequestDTO {

    @Schema(description = "Nombre del administrador", example = "Carlos Soto")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Correo electrónico del administrador", example = "carlos@smartpark.cl")
    @Email(message = "El email debe tener un formato valido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Schema(description = "Acción realizada por el administrador", example = "BLOQUEO_USUARIO")
    @NotBlank(message = "La accion es obligatoria")
    private String accion;

    @Schema(description = "Fecha y hora en que se realizó la acción", example = "2025-06-10T10:00:00")
    @NotNull(message = "La fecha de accion es obligatoria")
    private LocalDateTime fechaAccion;

    @Schema(description = "ID del usuario asociado a la acción", example = "3")
    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;
}