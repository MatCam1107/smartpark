package com.smartpark.ms_vehiculo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos necesarios para crear o actualizar un vehiculo")
public class VehiculoRequestDTO {

    @NotBlank(message = "La patente es obligatoria")
    @Schema(description = "Patente del vehiculo", example = "GHRT23")
    private String patente;

    @NotBlank(message = "La marca es obligatoria")
    @Schema(description = "Marca del vehiculo", example = "Toyota")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Schema(description = "Modelo del vehiculo", example = "Corolla")   
    private String modelo;

    @NotBlank(message = "El color es obligatorio")  
    @Schema(description = "Color del vehiculo", example = "Rojo")
    private String color;

    @NotNull(message = "El estado es obligatorio")
    @Schema(description = "Estado del vehiculo (true para activo, false para inactivo)", example = "true")
    private Boolean estado;

    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "ID del usuario dueño del vehiculo", example = "1")
    private Long usuarioId;
}