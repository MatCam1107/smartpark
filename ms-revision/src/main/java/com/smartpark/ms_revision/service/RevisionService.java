package com.smartpark.ms_revision.service;

import com.smartpark.ms_revision.dto.RevisionRequestDTO;
import com.smartpark.ms_revision.dto.RevisionResponseDTO;
import com.smartpark.ms_revision.model.Revision;
import com.smartpark.ms_revision.repository.RevisionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RevisionService {

    private final RevisionRepository revisionRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${ms.usuario.url}")
    private String usuarioUrl;

    @Value("${ms.estacionamiento.url}")
    private String estacionamientoUrl;

    public List<RevisionResponseDTO> obtenerTodas() {

        log.info("Obteniendo revisiones");

        return revisionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Optional<RevisionResponseDTO> obtenerPorId(Long id) {

        log.info("Buscando revision ID: {}", id);

        return revisionRepository.findById(id)
                .map(this::convertirADTO);
    }

    public RevisionResponseDTO guardar(RevisionRequestDTO dto) {

        try {

            validarUsuario(dto.getUsuarioId());
            validarEstacionamiento(dto.getEstacionamientoId());

            Revision revision = new Revision();

            revision.setCalificacion(dto.getCalificacion());
            revision.setComentario(dto.getComentario());
            revision.setFecha(dto.getFecha());
            revision.setUsuarioId(dto.getUsuarioId());
            revision.setEstacionamientoId(dto.getEstacionamientoId());

            Revision guardada = revisionRepository.save(revision);

            log.info("Revision creada correctamente");

            return convertirADTO(guardada);

        } catch (WebClientResponseException ex) {

            log.error("Error validando microservicios");

            throw new RuntimeException(
                    "No se pudo validar usuario o estacionamiento"
            );

        } catch (Exception ex) {

            log.error("Error al guardar revision");

            throw new RuntimeException(
                    "Error al guardar revision"
            );
        }
    }

    public Optional<RevisionResponseDTO> actualizar(
            Long id,
            RevisionRequestDTO dto) {

        try {

            validarUsuario(dto.getUsuarioId());
            validarEstacionamiento(dto.getEstacionamientoId());

            return revisionRepository.findById(id)
                    .map(r -> {

                        r.setCalificacion(dto.getCalificacion());
                        r.setComentario(dto.getComentario());
                        r.setFecha(dto.getFecha());
                        r.setUsuarioId(dto.getUsuarioId());
                        r.setEstacionamientoId(dto.getEstacionamientoId());

                        Revision actualizada =
                                revisionRepository.save(r);

                        log.info("Revision actualizada");

                        return convertirADTO(actualizada);
                    });

        } catch (WebClientResponseException ex) {

            log.error("Error validando microservicios");

            throw new RuntimeException(
                    "No se pudo validar usuario o estacionamiento"
            );

        } catch (Exception ex) {

            log.error("Error al actualizar revision");

            throw new RuntimeException(
                    "Error al actualizar revision"
            );
        }
    }

    public void eliminar(Long id) {

        log.info("Eliminando revision ID: {}", id);

        revisionRepository.deleteById(id);

        log.info("Revision eliminada");
    }

    private void validarUsuario(Long usuarioId) {

        webClientBuilder
                .baseUrl(usuarioUrl)
                .build()
                .get()
                .uri("/api/usuarios/{id}", usuarioId)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        log.info("Usuario validado");
    }

    private void validarEstacionamiento(Long estacionamientoId) {

        webClientBuilder
                .baseUrl(estacionamientoUrl)
                .build()
                .get()
                .uri("/api/estacionamientos/{id}", estacionamientoId)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        log.info("Estacionamiento validado");
    }

    private RevisionResponseDTO convertirADTO(Revision r) {

        return new RevisionResponseDTO(
                r.getIdRevision(),
                r.getCalificacion(),
                r.getComentario(),
                r.getFecha(),
                r.getUsuarioId(),
                r.getEstacionamientoId()
        );
    }
}