package com.smartpark.ms_revision.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para crear o actualizar una revision")
public class RevisionRequestDTO {

    @NotNull(message = "La calificacion es obligatoria")
    @Min(value = 1, message = "La calificacion minima es 1")
    @Max(value = 5, message = "La calificacion maxima es 5")
    @Schema(description = "Calificacion de 1 a 5", example = "5")
    private Integer calificacion;

    @NotBlank(message = "El comentario es obligatorio")
    @Schema(description = "Comentario de la revision", example = "Excelente servicio")
    private String comentario;

    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "Fecha de la revision", example = "2026-06-11")
    private LocalDate fecha;

    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "Id del usuario que califica", example = "1")
    private Long usuarioId;

    @NotNull(message = "El estacionamiento es obligatorio")
    @Schema(description = "Id del estacionamiento evaluado", example = "1")
    private Long estacionamientoId;
}