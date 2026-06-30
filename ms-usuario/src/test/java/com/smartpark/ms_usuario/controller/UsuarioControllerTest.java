package com.smartpark.ms_usuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartpark.ms_usuario.dto.UsuarioRequestDTO;
import com.smartpark.ms_usuario.dto.UsuarioResponseDTO;
import com.smartpark.ms_usuario.service.UsuarioService;
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

// @WebMvcTest levanta SOLO el controlador de usuarios (capa web).
// El servicio se reemplaza por un mock con @MockitoBean.
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    // En @WebMvcTest el ObjectMapper no se inyecta: se crea a mano.
    private final ObjectMapper objectMapper = new ObjectMapper();

    private UsuarioResponseDTO responseDTO;
    private UsuarioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new UsuarioResponseDTO(
                1L, "Juan", "Perez", "juan@smartpark.cl",
                "+56912345678", "CLIENTE", true
        );
        requestDTO = new UsuarioRequestDTO(
                "Juan", "Perez", "juan@smartpark.cl",
                "+56912345678", "CLIENTE", true
        );
    }

    @Test
    @DisplayName("GET /api/usuarios devuelve 200 y la lista")
    void obtenerTodos_ok() throws Exception {
        when(usuarioService.obtenerTodos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} devuelve 200 cuando existe")
    void obtenerPorId_existe() throws Exception {
        when(usuarioService.obtenerPorId(1L)).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.email").value("juan@smartpark.cl"));
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} devuelve 404 cuando NO existe")
    void obtenerPorId_noExiste() throws Exception {
        when(usuarioService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/usuarios devuelve 201 al crear")
    void crear_ok() throws Exception {
        when(usuarioService.guardar(any(UsuarioRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"));

        verify(usuarioService).guardar(any(UsuarioRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/usuarios devuelve 400 si el email es inválido")
    void crear_emailInvalido() throws Exception {
        UsuarioRequestDTO invalido = new UsuarioRequestDTO(
                "Juan", "Perez", "correo-malo",
                "+56912345678", "CLIENTE", true
        );

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/usuarios/{id} devuelve 200 cuando existe")
    void actualizar_existe() throws Exception {
        when(usuarioService.actualizar(eq(1L), any(UsuarioRequestDTO.class)))
                .thenReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apellido").value("Perez"));
    }

    @Test
    @DisplayName("PUT /api/usuarios/{id} devuelve 404 cuando NO existe")
    void actualizar_noExiste() throws Exception {
        when(usuarioService.actualizar(eq(99L), any(UsuarioRequestDTO.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/usuarios/{id} devuelve 204")
    void eliminar_ok() throws Exception {
        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService).eliminar(1L);
    }
}
