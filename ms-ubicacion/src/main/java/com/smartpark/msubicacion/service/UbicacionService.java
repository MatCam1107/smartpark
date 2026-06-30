package com.smartpark.msubicacion.service;

import com.smartpark.msubicacion.dto.UbicacionRequestDTO;
import com.smartpark.msubicacion.dto.UbicacionResponseDTO;
import com.smartpark.msubicacion.model.Ubicacion;
import com.smartpark.msubicacion.repository.UbicacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service → marca esta clase como capa de negocio.
//
// @RequiredArgsConstructor → Lombok genera constructor
// automático para todos los atributos final.
//
// @Slf4j → habilita logs con log.info(), log.error(), etc.
@Service
@RequiredArgsConstructor
@Slf4j
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    // Obtener todas las ubicaciones
    public List<UbicacionResponseDTO> obtenerTodas() {

        log.info("Obteniendo todas las ubicaciones");

        return ubicacionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    // Obtener ubicación por ID
    public Optional<UbicacionResponseDTO> obtenerPorId(Long id) {

        log.info("Buscando ubicación con ID: {}", id);

        return ubicacionRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Guardar nueva ubicación
    public UbicacionResponseDTO guardar(UbicacionRequestDTO dto) {

        log.info("Guardando nueva ubicación");

        Ubicacion ubicacion = new Ubicacion();

        ubicacion.setDireccion(dto.getDireccion());
        ubicacion.setComuna(dto.getComuna());
        ubicacion.setCiudad(dto.getCiudad());
        ubicacion.setRegion(dto.getRegion());
        ubicacion.setLatitud(dto.getLatitud());
        ubicacion.setLongitud(dto.getLongitud());

        Ubicacion guardada = ubicacionRepository.save(ubicacion);

        log.info("Ubicación guardada correctamente con ID: {}", guardada.getIdUbicacion());

        return convertirADTO(guardada);
    }

    // Actualizar ubicación
    public Optional<UbicacionResponseDTO> actualizar(Long id, UbicacionRequestDTO dto) {

        log.info("Actualizando ubicación con ID: {}", id);

        return ubicacionRepository.findById(id)
                .map(ubicacion -> {

                    ubicacion.setDireccion(dto.getDireccion());
                    ubicacion.setComuna(dto.getComuna());
                    ubicacion.setCiudad(dto.getCiudad());
                    ubicacion.setRegion(dto.getRegion());
                    ubicacion.setLatitud(dto.getLatitud());
                    ubicacion.setLongitud(dto.getLongitud());

                    Ubicacion actualizada = ubicacionRepository.save(ubicacion);

                    log.info("Ubicación actualizada correctamente");

                    return convertirADTO(actualizada);
                });
    }

    // Eliminar ubicación
    public void eliminar(Long id) {

        log.info("Eliminando ubicación con ID: {}", id);

        ubicacionRepository.deleteById(id);

        log.info("Ubicación eliminada correctamente");
    }

    // Convertir Entity → ResponseDTO
    private UbicacionResponseDTO convertirADTO(Ubicacion ubicacion) {

        return new UbicacionResponseDTO(
                ubicacion.getIdUbicacion(),
                ubicacion.getDireccion(),
                ubicacion.getComuna(),
                ubicacion.getCiudad(),
                ubicacion.getRegion(),
                ubicacion.getLatitud(),
                ubicacion.getLongitud()
        );
    }
}