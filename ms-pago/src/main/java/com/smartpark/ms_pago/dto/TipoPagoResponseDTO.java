package com.smartpark.ms_pago.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos retornados de un tipo de pago")
public class TipoPagoResponseDTO {

    @Schema(description = "ID único del tipo de pago", example = "1")
    private Long idTipoPago;

    @Schema(description = "Nombre del tipo de pago", example = "Tarjeta de crédito")
    private String nombreTipoPago;

    @Schema(description = "Descripción del tipo de pago", example = "Pago mediante tarjeta de crédito")
    private String descripcion;
}