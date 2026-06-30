package com.smartpark.msubicacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartpark.msubicacion.dto.UbicacionRequestDTO;
import com.smartpark.msubicacion.dto.UbicacionResponseDTO;
import com.smartpark.msubicacion.service.UbicacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest levanta SOLO el controlador de ubicaciones.
@WebMvcTest(UbicacionController.class)
class UbicacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UbicacionService ubicacionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private UbicacionResponseDTO responseDTO;
    private UbicacionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new UbicacionResponseDTO(
                1L, "Av. Vicuna Mackenna 4860", "Macul", "Santiago",
                "Metropolitana", -33.4489, -70.6693
        );

        requestDTO = new UbicacionRequestDTO();
        requestDTO.setDireccion("Av. Vicuna Mackenna 4860");
        requestDTO.setComuna("Macul");
        requestDTO.setCiudad("Santiago");
        requestDTO.setRegion("Metropolitana");
        requestDTO.setLatitud(-33.4489);
        requestDTO.setLongitud(-70.6693);
    }

    @Test
    @DisplayName("GET /api/ubicaciones devuelve 200 y la lista")
    void obtenerTodas_ok() throws Exception {
        when(ubicacionService.obtenerTodas()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/ubicaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comuna").value("Macul"));
    }

    @Test
    @DisplayName("GET /api/ubicaciones/{id} devuelve 200 cuando existe")
    void obtenerPorId_existe() throws Exception {
        when(ubicacionService.obtenerPorId(1L)).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/ubicaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUbicacion").value(1))
                .andExpect(jsonPath("$.ciudad").value("Santiago"));
    }

    @Test
    @DisplayName("GET /api/ubicaciones/{id} devuelve 404 cuando NO existe")
    void obtenerPorId_noExiste() throws Exception {
        when(ubicacionService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ubicaciones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/ubicaciones devuelve 201 al crear")
    void crear_ok() throws Exception {
        when(ubicacionService.guardar(any(UbicacionRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/ubicaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.direccion").value("Av. Vicuna Mackenna 4860"));

        verify(ubicacionService).guardar(any(UbicacionRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/ubicaciones devuelve 400 si la dirección está vacía")
    void crear_datosInvalidos() throws Exception {
        UbicacionRequestDTO invalido = new UbicacionRequestDTO();
        invalido.setDireccion(""); // viola @NotBlank
        invalido.setComuna("Macul");
        invalido.setCiudad("Santiago");
        invalido.setRegion("Metropolitana");
        invalido.setLatitud(-33.4489);
        invalido.setLongitud(-70.6693);

        mockMvc.perform(post("/api/ubicaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/ubicaciones/{id} devuelve 200 cuando existe")
    void actualizar_existe() throws Exception {
        when(ubicacionService.actualizar(eq(1L), any(UbicacionRequestDTO.class)))
                .thenReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/api/ubicaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/ubicaciones/{id} devuelve 404 cuando NO existe")
    void actualizar_noExiste() throws Exception {
        when(ubicacionService.actualizar(eq(99L), any(UbicacionRequestDTO.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/ubicaciones/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/ubicaciones/{id} devuelve 204")
    void eliminar_ok() throws Exception {
        doNothing().when(ubicacionService).eliminar(1L);

        mockMvc.perform(delete("/api/ubicaciones/1"))
                .andExpect(status().isNoContent());

        verify(ubicacionService).eliminar(1L);
    }
}
