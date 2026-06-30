package com.smartpark.ms_notificacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.smartpark.ms_notificacion.dto.NotificacionRequestDTO;
import com.smartpark.ms_notificacion.dto.NotificacionResponseDTO;
import com.smartpark.ms_notificacion.exception.RecursoNoEncontradoException;
import com.smartpark.ms_notificacion.model.Notificacion;
import com.smartpark.ms_notificacion.repository.NotificacionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${ms.usuario.url}")
    private String usuarioUrl;

    @Value("${ms.reserva.url}")
    private String reservaUrl;

    public List<NotificacionResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las notificaciones");

        return notificacionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Optional<NotificacionResponseDTO> obtenerPorId(Long id) {
        log.info("Buscando notificacion ID: {}", id);

        return notificacionRepository.findById(id)
                .map(this::convertirADTO);
    }

    public NotificacionResponseDTO guardar(NotificacionRequestDTO dto) {
        validarUsuario(dto.getUsuarioId());
        validarReserva(dto.getReservaId());

        Notificacion notificacion = new Notificacion();

        notificacion.setTipo(dto.getTipo());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setFechaEnvio(dto.getFechaEnvio());
        notificacion.setEstado(dto.getEstado());
        notificacion.setUsuarioId(dto.getUsuarioId());
        notificacion.setReservaId(dto.getReservaId());

        Notificacion guardada = notificacionRepository.save(notificacion);

        log.info("Notificacion creada correctamente");

        return convertirADTO(guardada);
    }

    public Optional<NotificacionResponseDTO> actualizar(Long id, NotificacionRequestDTO dto) {
        validarUsuario(dto.getUsuarioId());
        validarReserva(dto.getReservaId());

        return notificacionRepository.findById(id)
                .map(n -> {

                    n.setTipo(dto.getTipo());
                    n.setMensaje(dto.getMensaje());
                    n.setFechaEnvio(dto.getFechaEnvio());
                    n.setEstado(dto.getEstado());
                    n.setUsuarioId(dto.getUsuarioId());
                    n.setReservaId(dto.getReservaId());

                    Notificacion actualizada = notificacionRepository.save(n);

                    log.info("Notificacion actualizada");

                    return convertirADTO(actualizada);
                });
    }

    public void eliminar(Long id) {
        log.info("Eliminando notificacion ID: {}", id);
        notificacionRepository.deleteById(id);
        log.info("Notificacion eliminada");
    }

    private void validarUsuario(Long usuarioId) {
        try {
            webClientBuilder
                    .baseUrl(usuarioUrl)
                    .build()
                    .get()
                    .uri("/api/usuarios/{id}", usuarioId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            log.info("Usuario validado correctamente con ID: {}", usuarioId);

        } catch (WebClientResponseException.NotFound ex) {
            log.error("Usuario con ID {} no existe", usuarioId);
            throw new RecursoNoEncontradoException(
                    "El usuario con ID " + usuarioId + " no existe");

        } catch (WebClientResponseException ex) {
            log.error("Error al consultar ms-usuario. Status: {}", ex.getStatusCode());
            throw new RuntimeException(
                    "Error al comunicarse con el servicio de usuarios");
        }
    }

    private void validarReserva(Long reservaId) {
        try {
            webClientBuilder
                    .baseUrl(reservaUrl)
                    .build()
                    .get()
                    .uri("/api/reservas/{id}", reservaId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            log.info("Reserva validada correctamente con ID: {}", reservaId);

        } catch (WebClientResponseException.NotFound ex) {
            log.error("Reserva con ID {} no existe", reservaId);
            throw new RecursoNoEncontradoException(
                    "La reserva con ID " + reservaId + " no existe");

        } catch (WebClientResponseException ex) {
            log.error("Error al consultar ms-reserva. Status: {}", ex.getStatusCode());
            throw new RuntimeException(
                    "Error al comunicarse con el servicio de reservas");
        }
    }

    private NotificacionResponseDTO convertirADTO(Notificacion n) {
        return new NotificacionResponseDTO(
                n.getIdNotificacion(),
                n.getTipo(),
                n.getMensaje(),
                n.getFechaEnvio(),
                n.getEstado(),
                n.getUsuarioId(),
                n.getReservaId()
        );
    }
}