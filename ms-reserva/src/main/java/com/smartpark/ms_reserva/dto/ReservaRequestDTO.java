package com.smartpark.ms_reserva.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para crear o actualizar una reserva")
public class ReservaRequestDTO {

    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "Fecha de la reserva", example = "2026-06-10T00:00:00")
    private LocalDateTime fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Schema(description = "Hora de inicio de la reserva", example = "2026-06-10T09:00:00")
    private LocalDateTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    @Schema(description = "Hora de termino de la reserva", example = "2026-06-10T11:00:00")
    private LocalDateTime horaFin;

    @NotBlank(message = "El estado es obligatorio")
    @Schema(description = "Estado de la reserva", example = "CONFIRMADA")
    private String estado;

    @NotNull(message = "El usuario es obligatorio")
    @Schema(description = "Id del usuario que reserva", example = "1")
    private Long usuarioId;

    @NotNull(message = "El estacionamiento es obligatorio")
    @Schema(description = "Id del estacionamiento reservado", example = "1")
    private Long estacionamientoId;

    @Schema(description = "Id del pago asociado (opcional)", example = "1")
    private Long pagoId;
}