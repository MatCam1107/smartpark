package com.smartpark.ms_estacionamiento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos retornados de un estacionamiento")
public class EstacionamientoResponseDTO {

    @Schema(description = "ID único del estacionamiento", example = "1")
    private Long idEstacionamiento;

    @Schema(description = "Nombre del estacionamiento", example = "Estacionamiento Central")
    private String nombre;

    @Schema(description = "Descripción del estacionamiento", example = "Estacionamiento ubicado en el centro de la ciudad")
    private String descripcion;

    @Schema(description = "Tarifa por hora en pesos", example = "1500.0")
    private Double tarifaHora;

    @Schema(description = "Capacidad total de vehículos", example = "50")
    private Integer capacidad;

    @Schema(description = "Estado del estacionamiento (true = activo, false = inactivo)", example = "true")
    private Boolean estado;

    @Schema(description = "ID de la ubicación asociada", example = "1")
    private Long ubicacionId;

    @Schema(description = "ID del usuario administrador del estacionamiento", example = "2")
    private Long usuarioId;
}