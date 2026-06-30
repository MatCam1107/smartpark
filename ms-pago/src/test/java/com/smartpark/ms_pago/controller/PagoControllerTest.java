package com.smartpark.ms_pago.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smartpark.ms_pago.dto.PagoRequestDTO;
import com.smartpark.ms_pago.dto.PagoResponseDTO;
import com.smartpark.ms_pago.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest levanta SOLO el controlador de pagos.
@WebMvcTest(PagoController.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    // Registramos el modulo de fechas para serializar LocalDate.
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private PagoResponseDTO responseDTO;
    private PagoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new PagoResponseDTO(
                1L, 5000.0, LocalDate.of(2025, 6, 10), true, 1L, 3L
        );
        requestDTO = new PagoRequestDTO(
                5000.0, LocalDate.of(2025, 6, 10), true, 1L, 3L
        );
    }

    @Test
    @DisplayName("GET /api/pagos devuelve 200 y la lista")
    void obtenerTodos_ok() throws Exception {
        when(pagoService.obtenerTodos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].monto").value(5000.0));
    }

    @Test
    @DisplayName("GET /api/pagos/{id} devuelve 200 cuando existe")
    void obtenerPorId_existe() throws Exception {
        when(pagoService.obtenerPorId(1L)).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPago").value(1));
    }

    @Test
    @DisplayName("GET /api/pagos/{id} devuelve 404 cuando NO existe")
    void obtenerPorId_noExiste() throws Exception {
        when(pagoService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pagos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/pagos devuelve 201 al crear")
    void crear_ok() throws Exception {
        when(pagoService.guardar(any(PagoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.monto").value(5000.0));

        verify(pagoService).guardar(any(PagoRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/pagos devuelve 400 si el monto es negativo")
    void crear_montoInvalido() throws Exception {
        // monto negativo viola @Positive
        PagoRequestDTO invalido = new PagoRequestDTO(
                -100.0, LocalDate.of(2025, 6, 10), true, 1L, 3L
        );

        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/pagos/{id} devuelve 200 cuando existe")
    void actualizar_existe() throws Exception {
        when(pagoService.actualizar(eq(1L), any(PagoRequestDTO.class)))
                .thenReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/api/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/pagos/{id} devuelve 404 cuando NO existe")
    void actualizar_noExiste() throws Exception {
        when(pagoService.actualizar(eq(99L), any(PagoRequestDTO.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/pagos/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/pagos/{id} devuelve 204")
    void eliminar_ok() throws Exception {
        doNothing().when(pagoService).eliminar(1L);

        mockMvc.perform(delete("/api/pagos/1"))
                .andExpect(status().isNoContent());

        verify(pagoService).eliminar(1L);
    }
}
