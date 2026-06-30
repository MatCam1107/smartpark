package com.smartpark.ms_reserva.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representacion de una reserva devuelta por la API")
public class ReservaResponseDTO {

    @Schema(description = "Identificador unico de la reserva", example = "1")
    private Long idReserva;

    @Schema(description = "Fecha de la reserva", example = "2026-06-10T00:00:00")
    private LocalDateTime fecha;

    @Schema(description = "Hora de inicio de la reserva", example = "2026-06-10T09:00:00")
    private LocalDateTime horaInicio;

    @Schema(description = "Hora de termino de la reserva", example = "2026-06-10T11:00:00")
    private LocalDateTime horaFin;

    @Schema(description = "Estado de la reserva", example = "CONFIRMADA")
    private String estado;

    @Schema(description = "Id del usuario que reserva", example = "1")
    private Long usuarioId;

    @Schema(description = "Id del estacionamiento reservado", example = "1")
    private Long estacionamientoId;

    @Schema(description = "Id del pago asociado", example = "1")
    private Long pagoId;
}