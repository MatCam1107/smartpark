package com.smartpark.ms_pago.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos retornados de un pago")
public class PagoResponseDTO {

    @Schema(description = "ID único del pago", example = "1")
    private Long idPago;

    @Schema(description = "Monto del pago", example = "5000.0")
    private Double monto;

    @Schema(description = "Fecha en que se realizó el pago", example = "2025-06-10")
    private LocalDate fechaPago;

    @Schema(description = "Estado del pago (true = pagado, false = pendiente)", example = "true")
    private Boolean estado;

    @Schema(description = "ID del tipo de pago asociado", example = "1")
    private Long tipoPagoId;

    @Schema(description = "ID de la reserva asociada al pago", example = "3")
    private Long reservaId;
}