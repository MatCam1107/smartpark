package com.smartpark.ms_vehiculo.service;

import com.smartpark.ms_vehiculo.dto.VehiculoRequestDTO;
import com.smartpark.ms_vehiculo.dto.VehiculoResponseDTO;
import com.smartpark.ms_vehiculo.model.Vehiculo;
import com.smartpark.ms_vehiculo.repository.VehiculoRepository;
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
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${ms.usuario.url}")
    private String usuarioUrl;

    public List<VehiculoResponseDTO> obtenerTodos() {
        return vehiculoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Optional<VehiculoResponseDTO> obtenerPorId(Long id) {
        return vehiculoRepository.findById(id)
                .map(this::convertirADTO);
    }

    public VehiculoResponseDTO guardar(VehiculoRequestDTO dto) {
        try {
            validarUsuario(dto.getUsuarioId());

            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setPatente(dto.getPatente());
            vehiculo.setMarca(dto.getMarca());
            vehiculo.setModelo(dto.getModelo());
            vehiculo.setColor(dto.getColor());
            vehiculo.setEstado(dto.getEstado());
            vehiculo.setUsuarioId(dto.getUsuarioId());

            Vehiculo guardado = vehiculoRepository.save(vehiculo);

            log.info("Vehiculo creado correctamente");

            return convertirADTO(guardado);

        } catch (WebClientResponseException ex) {
            log.error("Error validando usuario");
            throw new RuntimeException("No se pudo validar el usuario indicado");
        } catch (Exception ex) {
            log.error("Error al guardar vehiculo");
            throw new RuntimeException("Error al guardar vehiculo");
        }
    }

    public Optional<VehiculoResponseDTO> actualizar(Long id, VehiculoRequestDTO dto) {
        try {
            validarUsuario(dto.getUsuarioId());

            return vehiculoRepository.findById(id)
                    .map(v -> {
                        v.setPatente(dto.getPatente());
                        v.setMarca(dto.getMarca());
                        v.setModelo(dto.getModelo());
                        v.setColor(dto.getColor());
                        v.setEstado(dto.getEstado());
                        v.setUsuarioId(dto.getUsuarioId());

                        Vehiculo actualizado = vehiculoRepository.save(v);

                        return convertirADTO(actualizado);
                    });

        } catch (WebClientResponseException ex) {
            log.error("Error validando usuario");
            throw new RuntimeException("No se pudo validar el usuario indicado");
        } catch (Exception ex) {
            log.error("Error al actualizar vehiculo");
            throw new RuntimeException("Error al actualizar vehiculo");
        }
    }

    public void eliminar(Long id) {
        vehiculoRepository.deleteById(id);
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

        log.info("Usuario validado correctamente");
    }

    private VehiculoResponseDTO convertirADTO(Vehiculo vehiculo) {
        return new VehiculoResponseDTO(
                vehiculo.getIdVehiculo(),
                vehiculo.getPatente(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getColor(),
                vehiculo.getEstado(),
                vehiculo.getUsuarioId()
        );
    }
}