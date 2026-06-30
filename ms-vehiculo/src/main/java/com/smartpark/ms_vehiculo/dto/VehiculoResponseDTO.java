package com.smartpark.ms_vehiculo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representacion de un vehiculo devuelto por la API")
public class VehiculoResponseDTO {

    @Schema(description = "Identificador unico del vehiculo", example = "1")
    private Long idVehiculo;

    @Schema(description = "Patente del vehiculo", example = "GHRT23")
    private String patente;

    @Schema(description = "Marca del vehiculo", example = "Toyota")
    private String marca;

    @Schema(description = "Modelo del vehiculo", example = "Corolla")
    private String modelo;

    @Schema(description = "Color del vehiculo", example = "Rojo")
    private String color;

    @Schema(description = "Estado del vehiculo (true para activo, false para inactivo)", example = "true")
    private Boolean estado;

    @Schema(description = "ID del usuario dueño del vehiculo", example = "1")
    private Long usuarioId;
}   