package com.smartpark.ms_notificacion.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos retornados de una notificación")
public class NotificacionResponseDTO {

    @Schema(description = "ID único de la notificación", example = "1")
    private Long idNotificacion;

    @Schema(description = "Tipo de notificación", example = "RESERVA_CONFIRMADA")
    private String tipo;

    @Schema(description = "Mensaje de la notificación", example = "Tu reserva ha sido confirmada exitosamente")
    private String mensaje;

    @Schema(description = "Fecha y hora de envío de la notificación", example = "2025-06-10T14:30:00")
    private LocalDateTime fechaEnvio;

    @Schema(description = "Estado de la notificación (true = enviada, false = pendiente)", example = "true")
    private Boolean estado;

    @Schema(description = "ID del usuario destinatario", example = "2")
    private Long usuarioId;

    @Schema(description = "ID de la reserva asociada", example = "5")
    private Long reservaId;
}