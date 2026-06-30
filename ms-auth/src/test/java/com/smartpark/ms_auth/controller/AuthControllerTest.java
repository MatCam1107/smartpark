package com.smartpark.ms_auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartpark.ms_auth.dto.AuthResponseDTO;
import com.smartpark.ms_auth.dto.LoginRequestDTO;
import com.smartpark.ms_auth.dto.RegisterRequestDTO;
import com.smartpark.ms_auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest levanta SOLO el controlador de autenticación.
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private AuthResponseDTO responseDTO;
    private RegisterRequestDTO registerDTO;
    private LoginRequestDTO loginDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new AuthResponseDTO(
                1L, "Juan Perez", "juan@smartpark.cl", "CLIENTE", "Login exitoso"
        );

        registerDTO = new RegisterRequestDTO();
        registerDTO.setNombre("Juan Perez");
        registerDTO.setEmail("juan@smartpark.cl");
        registerDTO.setPassword("password123");
        registerDTO.setRol("CLIENTE");

        loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("juan@smartpark.cl");
        loginDTO.setPassword("password123");
    }

    @Test
    @DisplayName("POST /api/auth/register devuelve 201")
    void registrar_ok() throws Exception {
        when(authService.registrar(any(RegisterRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("juan@smartpark.cl"));

        verify(authService).registrar(any(RegisterRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/auth/register devuelve 400 si el email es inválido")
    void registrar_emailInvalido() throws Exception {
        RegisterRequestDTO invalido = new RegisterRequestDTO();
        invalido.setNombre("Juan");
        invalido.setEmail("correo-malo"); // viola @Email
        invalido.setPassword("password123");
        invalido.setRol("CLIENTE");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/login devuelve 200")
    void login_ok() throws Exception {
        when(authService.login(any(LoginRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Login exitoso"));

        verify(authService).login(any(LoginRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/auth/login devuelve 400 si falta la password")
    void login_passwordVacia() throws Exception {
        LoginRequestDTO invalido = new LoginRequestDTO();
        invalido.setEmail("juan@smartpark.cl");
        invalido.setPassword(""); // viola @NotBlank

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }
}
