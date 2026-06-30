package com.smartpark.ms_vehiculo.service;

import com.smartpark.ms_vehiculo.dto.VehiculoResponseDTO;
import com.smartpark.ms_vehiculo.model.Vehiculo;
import com.smartpark.ms_vehiculo.repository.VehiculoRepository;
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

// Prueba unitaria del servicio de vehiculos con Mockito.
// Se prueban las operaciones de lectura/borrado (no usan WebClient).
// El WebClient.Builder se mockea pero no se invoca en estos metodos.
@ExtendWith(MockitoExtension.class)
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private VehiculoService vehiculoService;

    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo(
                1L, "ABCD12", "Toyota", "Yaris", "Rojo", true, 3L
        );
    }

    @Test
    @DisplayName("obtenerTodos devuelve la lista de vehiculos")
    void obtenerTodos_devuelveLista() {
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));

        List<VehiculoResponseDTO> resultado = vehiculoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("ABCD12", resultado.get(0).getPatente());
        verify(vehiculoRepository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId devuelve el vehiculo cuando existe")
    void obtenerPorId_existe() {
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));

        Optional<VehiculoResponseDTO> resultado = vehiculoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Toyota", resultado.get().getMarca());
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío cuando NO existe")
    void obtenerPorId_noExiste() {
        when(vehiculoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<VehiculoResponseDTO> resultado = vehiculoService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_llamaRepositorio() {
        doNothing().when(vehiculoRepository).deleteById(1L);

        vehiculoService.eliminar(1L);

        verify(vehiculoRepository, times(1)).deleteById(1L);
    }
}
