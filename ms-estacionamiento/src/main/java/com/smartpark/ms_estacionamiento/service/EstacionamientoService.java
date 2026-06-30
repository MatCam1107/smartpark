package com.smartpark.ms_estacionamiento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.smartpark.ms_estacionamiento.dto.EstacionamientoRequestDTO;
import com.smartpark.ms_estacionamiento.dto.EstacionamientoResponseDTO;
import com.smartpark.ms_estacionamiento.exception.RecursoNoEncontradoException;
import com.smartpark.ms_estacionamiento.model.Estacionamiento;
import com.smartpark.ms_estacionamiento.repository.EstacionamientoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstacionamientoService {

    private final EstacionamientoRepository repository;
    private final WebClient webClient;

    public List<EstacionamientoResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los estacionamientos");

        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Optional<EstacionamientoResponseDTO> obtenerPorId(Long id) {
        log.info("Buscando estacionamiento con ID: {}", id);

        return repository.findById(id)
                .map(this::convertirADTO);
    }

    public EstacionamientoResponseDTO guardar(EstacionamientoRequestDTO dto) {
        log.info("Validando ubicación ID: {}", dto.getUbicacionId());

        validarUbicacion(dto.getUbicacionId());

        Estacionamiento estacionamiento = new Estacionamiento();

        estacionamiento.setNombre(dto.getNombre());
        estacionamiento.setDescripcion(dto.getDescripcion());
        estacionamiento.setTarifaHora(dto.getTarifaHora());
        estacionamiento.setCapacidad(dto.getCapacidad());
        estacionamiento.setEstado(dto.getEstado());
        estacionamiento.setUbicacionId(dto.getUbicacionId());
        estacionamiento.setUsuarioId(dto.getUsuarioId());

        Estacionamiento guardado = repository.save(estacionamiento);

        log.info("Estacionamiento creado correctamente con ID: {}", guardado.getIdEstacionamiento());

        return convertirADTO(guardado);
    }

    public Optional<EstacionamientoResponseDTO> actualizar(Long id, EstacionamientoRequestDTO dto) {
        log.info("Actualizando estacionamiento ID: {}", id);

        validarUbicacion(dto.getUbicacionId());

        return repository.findById(id)
                .map(estacionamiento -> {

                    estacionamiento.setNombre(dto.getNombre());
                    estacionamiento.setDescripcion(dto.getDescripcion());
                    estacionamiento.setTarifaHora(dto.getTarifaHora());
                    estacionamiento.setCapacidad(dto.getCapacidad());
                    estacionamiento.setEstado(dto.getEstado());
                    estacionamiento.setUbicacionId(dto.getUbicacionId());
                    estacionamiento.setUsuarioId(dto.getUsuarioId());

                    Estacionamiento actualizado = repository.save(estacionamiento);

                    log.info("Estacionamiento actualizado correctamente");

                    return convertirADTO(actualizado);
                });
    }

    public void eliminar(Long id) {
        log.info("Eliminando estacionamiento ID: {}", id);
        repository.deleteById(id);
        log.info("Estacionamiento eliminado correctamente");
    }

    private void validarUbicacion(Long ubicacionId) {
        try {
            webClient.get()
                    .uri("/api/ubicaciones/{id}", ubicacionId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            log.info("Ubicación validada correctamente con ID: {}", ubicacionId);

        } catch (WebClientResponseException.NotFound ex) {
            log.error("Ubicación con ID {} no existe", ubicacionId);
            throw new RecursoNoEncontradoException(
                    "La ubicación con ID " + ubicacionId + " no existe");

        } catch (WebClientResponseException ex) {
            log.error("Error al consultar ms-ubicacion. Status: {}", ex.getStatusCode());
            throw new RuntimeException(
                    "Error al comunicarse con el servicio de ubicaciones");
        }
    }

    private EstacionamientoResponseDTO convertirADTO(Estacionamiento estacionamiento) {
        return new EstacionamientoResponseDTO(
                estacionamiento.getIdEstacionamiento(),
                estacionamiento.getNombre(),
                estacionamiento.getDescripcion(),
                estacionamiento.getTarifaHora(),
                estacionamiento.getCapacidad(),
                estacionamiento.getEstado(),
                estacionamiento.getUbicacionId(),
                estacionamiento.getUsuarioId()
        );
    }
}