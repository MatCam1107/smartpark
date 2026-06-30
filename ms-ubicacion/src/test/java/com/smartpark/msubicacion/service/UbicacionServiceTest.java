package com.smartpark.msubicacion.service;

import com.smartpark.msubicacion.dto.UbicacionRequestDTO;
import com.smartpark.msubicacion.dto.UbicacionResponseDTO;
import com.smartpark.msubicacion.model.Ubicacion;
import com.smartpark.msubicacion.repository.UbicacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Prueba unitaria del servicio de ubicaciones con Mockito (sin base de datos).
@ExtendWith(MockitoExtension.class)
class UbicacionServiceTest {

    @Mock
    private UbicacionRepository ubicacionRepository;

    @InjectMocks
    private UbicacionService ubicacionService;

    private Ubicacion ubicacion;
    private UbicacionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ubicacion = new Ubicacion(
                1L, "Av. Vicuna Mackenna 4860", "Macul", "Santiago",
                "Metropolitana", -33.4489, -70.6693
        );

        // UbicacionRequestDTO solo tiene @Data (sin all-args), usamos setters.
        requestDTO = new UbicacionRequestDTO();
        requestDTO.setDireccion("Av. Vicuna Mackenna 4860");
        requestDTO.setComuna("Macul");
        requestDTO.setCiudad("Santiago");
        requestDTO.setRegion("Metropolitana");
        requestDTO.setLatitud(-33.4489);
        requestDTO.setLongitud(-70.6693);
    }

    @Test
    @DisplayName("obtenerTodas devuelve la lista de ubicaciones")
    void obtenerTodas_devuelveLista() {
        when(ubicacionRepository.findAll()).thenReturn(List.of(ubicacion));

        List<UbicacionResponseDTO> resultado = ubicacionService.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals("Macul", resultado.get(0).getComuna());
        verify(ubicacionRepository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId devuelve la ubicación cuando existe")
    void obtenerPorId_existe() {
        when(ubicacionRepository.findById(1L)).thenReturn(Optional.of(ubicacion));

        Optional<UbicacionResponseDTO> resultado = ubicacionService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Santiago", resultado.get().getCiudad());
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío cuando NO existe")
    void obtenerPorId_noExiste() {
        when(ubicacionRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UbicacionResponseDTO> resultado = ubicacionService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("guardar crea y devuelve la ubicación")
    void guardar_creaUbicacion() {
        when(ubicacionRepository.save(any(Ubicacion.class))).thenReturn(ubicacion);

        UbicacionResponseDTO resultado = ubicacionService.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals("Av. Vicuna Mackenna 4860", resultado.getDireccion());
        assertEquals(1L, resultado.getIdUbicacion());
        verify(ubicacionRepository).save(any(Ubicacion.class));
    }

    @Test
    @DisplayName("actualizar modifica la ubicación cuando existe")
    void actualizar_existe() {
        when(ubicacionRepository.findById(1L)).thenReturn(Optional.of(ubicacion));
        when(ubicacionRepository.save(any(Ubicacion.class))).thenReturn(ubicacion);

        Optional<UbicacionResponseDTO> resultado = ubicacionService.actualizar(1L, requestDTO);

        assertTrue(resultado.isPresent());
        verify(ubicacionRepository).save(any(Ubicacion.class));
    }

    @Test
    @DisplayName("actualizar devuelve vacío cuando NO existe")
    void actualizar_noExiste() {
        when(ubicacionRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UbicacionResponseDTO> resultado = ubicacionService.actualizar(99L, requestDTO);

        assertTrue(resultado.isEmpty());
        verify(ubicacionRepository, never()).save(any(Ubicacion.class));
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_llamaRepositorio() {
        doNothing().when(ubicacionRepository).deleteById(1L);

        ubicacionService.eliminar(1L);

        verify(ubicacionRepository, times(1)).deleteById(1L);
    }
}
