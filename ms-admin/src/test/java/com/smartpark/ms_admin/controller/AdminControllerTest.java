package com.smartpark.ms_admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smartpark.ms_admin.dto.AdminRequestDTO;
import com.smartpark.ms_admin.dto.AdminResponseDTO;
import com.smartpark.ms_admin.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Prueba del controlador (capa web). @WebMvcTest levanta SOLO el controlador,
// no la base de datos. El servicio se reemplaza por un mock con @MockitoBean.
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminService adminService;

    // En @WebMvcTest el ObjectMapper no se inyecta: lo creamos a mano.
    // Registramos el modulo de fechas para poder serializar LocalDateTime.
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private AdminResponseDTO responseDTO;
    private AdminRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new AdminResponseDTO(
                1L,
                "Carlos Soto",
                "carlos@smartpark.cl",
                "BLOQUEO_USUARIO",
                LocalDateTime.of(2025, 6, 10, 10, 0),
                3L
        );

        requestDTO = new AdminRequestDTO(
                "Carlos Soto",
                "carlos@smartpark.cl",
                "BLOQUEO_USUARIO",
                LocalDateTime.of(2025, 6, 10, 10, 0),
                3L
        );
    }

    @Test
    @DisplayName("GET /api/admins devuelve 200 y la lista")
    void obtenerTodos_ok() throws Exception {
        when(adminService.obtenerTodos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Carlos Soto"))
                .andExpect(jsonPath("$[0].email").value("carlos@smartpark.cl"));
    }

    @Test
    @DisplayName("GET /api/admins/{id} devuelve 200 cuando existe")
    void obtenerPorId_existe() throws Exception {
        when(adminService.obtenerPorId(1L)).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/admins/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAdmin").value(1))
                .andExpect(jsonPath("$.nombre").value("Carlos Soto"));
    }

    @Test
    @DisplayName("GET /api/admins/{id} devuelve 404 cuando NO existe")
    void obtenerPorId_noExiste() throws Exception {
        when(adminService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/admins/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/admins devuelve 201 al crear")
    void crear_ok() throws Exception {
        when(adminService.guardar(any(AdminRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Carlos Soto"));

        verify(adminService).guardar(any(AdminRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/admins devuelve 400 si el nombre está vacío")
    void crear_datosInvalidos() throws Exception {
        // nombre en blanco -> falla la validacion @NotBlank
        AdminRequestDTO invalido = new AdminRequestDTO(
                "", "carlos@smartpark.cl", "BLOQUEO_USUARIO",
                LocalDateTime.of(2025, 6, 10, 10, 0), 3L
        );

        mockMvc.perform(post("/api/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/admins/{id} devuelve 200 cuando existe")
    void actualizar_existe() throws Exception {
        when(adminService.actualizar(eq(1L), any(AdminRequestDTO.class)))
                .thenReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/api/admins/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Soto"));
    }

    @Test
    @DisplayName("PUT /api/admins/{id} devuelve 404 cuando NO existe")
    void actualizar_noExiste() throws Exception {
        when(adminService.actualizar(eq(99L), any(AdminRequestDTO.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/admins/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/admins/{id} devuelve 204")
    void eliminar_ok() throws Exception {
        doNothing().when(adminService).eliminar(1L);

        mockMvc.perform(delete("/api/admins/1"))
                .andExpect(status().isNoContent());

        verify(adminService).eliminar(1L);
    }
}
