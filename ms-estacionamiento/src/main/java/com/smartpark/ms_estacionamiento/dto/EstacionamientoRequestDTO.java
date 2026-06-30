package com.smartpark.ms_estacionamiento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear o actualizar un estacionamiento")
public class EstacionamientoRequestDTO {

    @Schema(description = "Nombre del estacionamiento", example = "Estacionamiento Central")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Descripción del estacionamiento", example = "Estacionamiento ubicado en el centro de la ciudad")
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @Schema(description = "Tarifa por hora en pesos", example = "1500.0")
    @NotNull(message = "La tarifa por hora es obligatoria")
    @Positive(message = "La tarifa debe ser mayor a 0")
    private Double tarifaHora;

    @Schema(description = "Capacidad total de vehículos", example = "50")
    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    @Schema(description = "Estado del estacionamiento (true = activo, false = inactivo)", example = "true")
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @Schema(description = "ID de la ubicación asociada", example = "1")
    @NotNull(message = "La ubicacion es obligatoria")
    private Long ubicacionId;

    @Schema(description = "ID del usuario administrador del estacionamiento", example = "2")
    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;
}