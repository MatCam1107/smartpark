package com.smartpark.ms_pago.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Datos requeridos para crear o actualizar un pago")
public class PagoRequestDTO {

    @Schema(description = "Monto del pago", example = "5000.0")
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;

    @Schema(description = "Fecha en que se realizó el pago", example = "2025-06-10")
    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;

    @Schema(description = "Estado del pago (true = pagado, false = pendiente)", example = "true")
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @Schema(description = "ID del tipo de pago asociado", example = "1")
    @NotNull(message = "El tipo de pago es obligatorio")
    private Long tipoPagoId;

    @Schema(description = "ID de la reserva asociada al pago", example = "3")
    @NotNull(message = "La reserva es obligatoria")
    private Long reservaId;
}