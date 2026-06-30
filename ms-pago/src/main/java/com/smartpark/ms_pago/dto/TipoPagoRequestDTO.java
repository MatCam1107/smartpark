package com.smartpark.ms_pago.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Datos requeridos para crear o actualizar un tipo de pago")
public class TipoPagoRequestDTO {

    @Schema(description = "Nombre del tipo de pago", example = "Tarjeta de crédito")
    @NotBlank(message = "El nombre del tipo de pago es obligatorio")
    private String nombreTipoPago;

    @Schema(description = "Descripción del tipo de pago", example = "Pago mediante tarjeta de crédito")
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;
}