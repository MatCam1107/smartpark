package com.smartpark.ms_reserva.service;

import com.smartpark.ms_reserva.dto.ReservaResponseDTO;
import com.smartpark.ms_reserva.model.Reserva;
import com.smartpark.ms_reserva.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Prueba unitaria del servicio de reservas con Mockito.
// Se prueban las operaciones de lectura/borrado (no usan WebClient).
@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        LocalDateTime base = LocalDateTime.of(2025, 6, 10, 10, 0);
        reserva = new Reserva(
                1L, base, base, base.plusHours(2), "CONFIRMADA", 3L, 2L, 5L
        );
    }

    @Test
    @DisplayName("obtenerTodas devuelve la lista de reservas")
    void obtenerTodas_devuelveLista() {
        when(reservaRepository.findAll()).thenReturn(List.of(reserva));

        List<ReservaResponseDTO> resultado = reservaService.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals("CONFIRMADA", resultado.get(0).getEstado());
        verify(reservaRepository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId devuelve la reserva cuando existe")
    void obtenerPorId_existe() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Optional<ReservaResponseDTO> resultado = reservaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(3L, resultado.get().getUsuarioId());
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío cuando NO existe")
    void obtenerPorId_noExiste() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ReservaResponseDTO> resultado = reservaService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_llamaRepositorio() {
        doNothing().when(reservaRepository).deleteById(1L);

        reservaService.eliminar(1L);

        verify(reservaRepository, times(1)).deleteById(1L);
    }
}
