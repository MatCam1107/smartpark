package com.smartpark.ms_notificacion.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear o actualizar una notificación")
public class NotificacionRequestDTO {

    @Schema(description = "Tipo de notificación", example = "RESERVA_CONFIRMADA")
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @Schema(description = "Mensaje de la notificación", example = "Tu reserva ha sido confirmada exitosamente")
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @Schema(description = "Fecha y hora de envío de la notificación", example = "2025-06-10T14:30:00")
    @NotNull(message = "La fecha de envio es obligatoria")
    private LocalDateTime fechaEnvio;

    @Schema(description = "Estado de la notificación (true = enviada, false = pendiente)", example = "true")
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @Schema(description = "ID del usuario destinatario", example = "2")
    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @Schema(description = "ID de la reserva asociada", example = "5")
    @NotNull(message = "La reserva es obligatoria")
    private Long reservaId;
}