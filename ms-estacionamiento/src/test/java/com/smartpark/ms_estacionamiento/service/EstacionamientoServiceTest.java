package com.smartpark.ms_estacionamiento.service;

import com.smartpark.ms_estacionamiento.dto.EstacionamientoResponseDTO;
import com.smartpark.ms_estacionamiento.model.Estacionamiento;
import com.smartpark.ms_estacionamiento.repository.EstacionamientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Prueba unitaria del servicio de estacionamientos con Mockito.
// Se prueban las operaciones de lectura/borrado (no usan WebClient).
@ExtendWith(MockitoExtension.class)
class EstacionamientoServiceTest {

    @Mock
    private EstacionamientoRepository repository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private EstacionamientoService estacionamientoService;

    private Estacionamiento estacionamiento;

    @BeforeEach
    void setUp() {
        estacionamiento = new Estacionamiento(
                1L, "Parking Centro", "Estacionamiento techado", 1500.0,
                50, true, 2L, 3L
        );
    }

    @Test
    @DisplayName("obtenerTodos devuelve la lista de estacionamientos")
    void obtenerTodos_devuelveLista() {
        when(repository.findAll()).thenReturn(List.of(estacionamiento));

        List<EstacionamientoResponseDTO> resultado = estacionamientoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Parking Centro", resultado.get(0).getNombre());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId devuelve el estacionamiento cuando existe")
    void obtenerPorId_existe() {
        when(repository.findById(1L)).thenReturn(Optional.of(estacionamiento));

        Optional<EstacionamientoResponseDTO> resultado = estacionamientoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1500.0, resultado.get().getTarifaHora());
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío cuando NO existe")
    void obtenerPorId_noExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<EstacionamientoResponseDTO> resultado = estacionamientoService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_llamaRepositorio() {
        doNothing().when(repository).deleteById(1L);

        estacionamientoService.eliminar(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
