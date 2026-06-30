package com.smartpark.ms_revision.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representacion de una revision devuelta por la API")
public class RevisionResponseDTO {

    @Schema(description = "Identificador unico de la revision", example = "1")
    private Long idRevision;

    @Schema(description = "Calificacion de 1 a 5", example = "5")
    private Integer calificacion;

    @Schema(description = "Comentario de la revision", example = "Excelente servicio")
    private String comentario;

    @Schema(description = "Fecha de la revision", example = "2026-06-11")
    private LocalDate fecha;

    @Schema(description = "Id del usuario que califica", example = "1")
    private Long usuarioId;

    @Schema(description = "Id del estacionamiento evaluado", example = "1")
    private Long estacionamientoId;
}