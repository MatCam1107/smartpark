package com.smartpark.ms_pago.service;

import com.smartpark.ms_pago.dto.PagoRequestDTO;
import com.smartpark.ms_pago.dto.PagoResponseDTO;
import com.smartpark.ms_pago.model.Pago;
import com.smartpark.ms_pago.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Prueba unitaria del servicio de pagos con Mockito (sin base de datos).
@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;
    private PagoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        pago = new Pago(
                1L, 5000.0, LocalDate.of(2025, 6, 10), true, 1L, 3L
        );

        requestDTO = new PagoRequestDTO(
                5000.0, LocalDate.of(2025, 6, 10), true, 1L, 3L
        );
    }

    @Test
    @DisplayName("obtenerTodos devuelve la lista de pagos")
    void obtenerTodos_devuelveLista() {
        when(pagoRepository.findAll()).thenReturn(List.of(pago));

        List<PagoResponseDTO> resultado = pagoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals(5000.0, resultado.get(0).getMonto());
        verify(pagoRepository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId devuelve el pago cuando existe")
    void obtenerPorId_existe() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        Optional<PagoResponseDTO> resultado = pagoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertTrue(resultado.get().getEstado());
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío cuando NO existe")
    void obtenerPorId_noExiste() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<PagoResponseDTO> resultado = pagoService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("guardar crea y devuelve el pago")
    void guardar_creaPago() {
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        PagoResponseDTO resultado = pagoService.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals(5000.0, resultado.getMonto());
        assertEquals(1L, resultado.getIdPago());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    @DisplayName("actualizar modifica el pago cuando existe")
    void actualizar_existe() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        Optional<PagoResponseDTO> resultado = pagoService.actualizar(1L, requestDTO);

        assertTrue(resultado.isPresent());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    @DisplayName("actualizar devuelve vacío cuando NO existe")
    void actualizar_noExiste() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<PagoResponseDTO> resultado = pagoService.actualizar(99L, requestDTO);

        assertTrue(resultado.isEmpty());
        verify(pagoRepository, never()).save(any(Pago.class));
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_llamaRepositorio() {
        doNothing().when(pagoRepository).deleteById(1L);

        pagoService.eliminar(1L);

        verify(pagoRepository, times(1)).deleteById(1L);
    }
}
