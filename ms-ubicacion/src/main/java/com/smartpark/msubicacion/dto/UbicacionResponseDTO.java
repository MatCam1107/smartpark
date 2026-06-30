package com.smartpark.msubicacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representacion de una ubicacion devuelta por la API")
public class UbicacionResponseDTO {

    @Schema(description = "Identificador unico de la ubicacion", example = "1")
    private Long idUbicacion;

    @Schema(description = "Direccion de la ubicacion", example = "Av. Vicuna Mackenna 4860")
    private String direccion;

    @Schema(description = "Comuna", example = "Macul")
    private String comuna;

    @Schema(description = "Ciudad", example = "Santiago")
    private String ciudad;

    @Schema(description = "Region", example = "Metropolitana")
    private String region;

    @Schema(description = "Latitud geografica", example = "-33.4489")
    private Double latitud;

    @Schema(description = "Longitud geografica", example = "-70.6693")
    private Double longitud;
}