package com.smartpark.ms_notificacion.service;

import com.smartpark.ms_notificacion.dto.NotificacionResponseDTO;
import com.smartpark.ms_notificacion.model.Notificacion;
import com.smartpark.ms_notificacion.repository.NotificacionRepository;
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

// Prueba unitaria del servicio de notificaciones con Mockito.
// Se prueban las operaciones de lectura/borrado (no usan WebClient).
@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion(
                1L, "RESERVA", "Tu reserva fue confirmada",
                LocalDateTime.of(2025, 6, 10, 10, 0), true, 3L, 5L
        );
    }

    @Test
    @DisplayName("obtenerTodas devuelve la lista de notificaciones")
    void obtenerTodas_devuelveLista() {
        when(notificacionRepository.findAll()).thenReturn(List.of(notificacion));

        List<NotificacionResponseDTO> resultado = notificacionService.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals("RESERVA", resultado.get(0).getTipo());
        verify(notificacionRepository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId devuelve la notificación cuando existe")
    void obtenerPorId_existe() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));

        Optional<NotificacionResponseDTO> resultado = notificacionService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Tu reserva fue confirmada", resultado.get().getMensaje());
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío cuando NO existe")
    void obtenerPorId_noExiste() {
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<NotificacionResponseDTO> resultado = notificacionService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_llamaRepositorio() {
        doNothing().when(notificacionRepository).deleteById(1L);

        notificacionService.eliminar(1L);

        verify(notificacionRepository, times(1)).deleteById(1L);
    }
}
