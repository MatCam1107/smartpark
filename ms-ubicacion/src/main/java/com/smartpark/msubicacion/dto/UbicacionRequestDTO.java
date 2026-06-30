package com.smartpark.msubicacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para crear o actualizar una ubicacion")
public class UbicacionRequestDTO {

    @NotBlank(message = "La direccion es obligatoria")
    @Schema(description = "Direccion de la ubicacion", example = "Av. Vicuna Mackenna 4860")
    private String direccion;

    @NotBlank(message = "La comuna es obligatoria")
    @Schema(description = "Comuna", example = "Macul")
    private String comuna;

    @NotBlank(message = "La ciudad es obligatoria")
    @Schema(description = "Ciudad", example = "Santiago")
    private String ciudad;

    @NotBlank(message = "La region es obligatoria")
    @Schema(description = "Region", example = "Metropolitana")
    private String region;

    @NotNull(message = "La latitud es obligatoria")
    @Schema(description = "Latitud geografica", example = "-33.4489")
    private Double latitud;

    @NotNull(message = "La longitud es obligatoria")
    @Schema(description = "Longitud geografica", example = "-70.6693")
    private Double longitud;
}