package com.smartpark.ms_reserva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.smartpark.ms_reserva.dto.ReservaRequestDTO;
import com.smartpark.ms_reserva.dto.ReservaResponseDTO;
import com.smartpark.ms_reserva.exception.RecursoNoEncontradoException;
import com.smartpark.ms_reserva.model.Reserva;
import com.smartpark.ms_reserva.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${ms.usuario.url}")
    private String usuarioUrl;

    @Value("${ms.estacionamiento.url}")
    private String estacionamientoUrl;

    public List<ReservaResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las reservas");

        return reservaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Optional<ReservaResponseDTO> obtenerPorId(Long id) {
        log.info("Buscando reserva con ID: {}", id);

        return reservaRepository.findById(id)
                .map(this::convertirADTO);
    }

    public ReservaResponseDTO guardar(ReservaRequestDTO dto) {
        log.info("Validando usuario ID: {}", dto.getUsuarioId());
        validarUsuario(dto.getUsuarioId());

        log.info("Validando estacionamiento ID: {}", dto.getEstacionamientoId());
        validarEstacionamiento(dto.getEstacionamientoId());

        Reserva reserva = new Reserva();

        reserva.setFecha(dto.getFecha());
        reserva.setHoraInicio(dto.getHoraInicio());
        reserva.setHoraFin(dto.getHoraFin());
        reserva.setEstado(dto.getEstado());
        reserva.setUsuarioId(dto.getUsuarioId());
        reserva.setEstacionamientoId(dto.getEstacionamientoId());
        reserva.setPagoId(dto.getPagoId());

        Reserva guardada = reservaRepository.save(reserva);

        log.info("Reserva creada correctamente con ID: {}", guardada.getIdReserva());

        return convertirADTO(guardada);
    }

    public Optional<ReservaResponseDTO> actualizar(Long id, ReservaRequestDTO dto) {
        log.info("Actualizando reserva ID: {}", id);

        validarUsuario(dto.getUsuarioId());
        validarEstacionamiento(dto.getEstacionamientoId());

        return reservaRepository.findById(id)
                .map(reserva -> {

                    reserva.setFecha(dto.getFecha());
                    reserva.setHoraInicio(dto.getHoraInicio());
                    reserva.setHoraFin(dto.getHoraFin());
                    reserva.setEstado(dto.getEstado());
                    reserva.setUsuarioId(dto.getUsuarioId());
                    reserva.setEstacionamientoId(dto.getEstacionamientoId());
                    reserva.setPagoId(dto.getPagoId());

                    Reserva actualizada = reservaRepository.save(reserva);

                    log.info("Reserva actualizada correctamente");

                    return convertirADTO(actualizada);
                });
    }

    public void eliminar(Long id) {
        log.info("Eliminando reserva ID: {}", id);
        reservaRepository.deleteById(id);
        log.info("Reserva eliminada correctamente");
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

    private void validarEstacionamiento(Long estacionamientoId) {
        try {
            webClientBuilder
                    .baseUrl(estacionamientoUrl)
                    .build()
                    .get()
                    .uri("/api/estacionamientos/{id}", estacionamientoId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            log.info("Estacionamiento validado correctamente con ID: {}", estacionamientoId);

        } catch (WebClientResponseException.NotFound ex) {
            log.error("Estacionamiento con ID {} no existe", estacionamientoId);
            throw new RecursoNoEncontradoException(
                    "El estacionamiento con ID " + estacionamientoId + " no existe");

        } catch (WebClientResponseException ex) {
            log.error("Error al consultar ms-estacionamiento. Status: {}", ex.getStatusCode());
            throw new RuntimeException(
                    "Error al comunicarse con el servicio de estacionamientos");
        }
    }

    private ReservaResponseDTO convertirADTO(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getIdReserva(),
                reserva.getFecha(),
                reserva.getHoraInicio(),
                reserva.getHoraFin(),
                reserva.getEstado(),
                reserva.getUsuarioId(),
                reserva.getEstacionamientoId(),
                reserva.getPagoId()
        );
    }
}