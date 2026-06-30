package com.smartpark.ms_auth.service;

import com.smartpark.ms_auth.dto.AuthResponseDTO;
import com.smartpark.ms_auth.dto.LoginRequestDTO;
import com.smartpark.ms_auth.dto.RegisterRequestDTO;
import com.smartpark.ms_auth.model.AuthUser;
import com.smartpark.ms_auth.repository.AuthUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Prueba unitaria del servicio de autenticación con Mockito.
// Aquí se validan las REGLAS DE NEGOCIO clave: email duplicado y password incorrecta.
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthUserRepository authUserRepository;

    @InjectMocks
    private AuthService authService;

    private AuthUser usuarioExistente;
    private RegisterRequestDTO registerDTO;
    private LoginRequestDTO loginDTO;

    @BeforeEach
    void setUp() {
        usuarioExistente = new AuthUser(
                1L, "Juan Perez", "juan@smartpark.cl", "password123", "CLIENTE"
        );

        // DTOs solo con @Data -> usamos setters
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
    @DisplayName("registrar crea el usuario cuando el email NO existe")
    void registrar_emailNuevo_ok() {
        when(authUserRepository.findByEmail("juan@smartpark.cl")).thenReturn(Optional.empty());
        when(authUserRepository.save(any(AuthUser.class))).thenReturn(usuarioExistente);

        AuthResponseDTO resultado = authService.registrar(registerDTO);

        assertNotNull(resultado);
        assertEquals("juan@smartpark.cl", resultado.getEmail());
        assertEquals("Usuario registrado correctamente", resultado.getMensaje());
        verify(authUserRepository).save(any(AuthUser.class));
    }

    @Test
    @DisplayName("registrar lanza excepción cuando el email YA existe")
    void registrar_emailDuplicado_lanzaExcepcion() {
        when(authUserRepository.findByEmail("juan@smartpark.cl"))
                .thenReturn(Optional.of(usuarioExistente));

        // REGLA DE NEGOCIO: no se puede registrar un email repetido
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.registrar(registerDTO));
        assertEquals("El email ya se encuentra registrado", ex.getMessage());

        // nunca debe guardar si el email ya existe
        verify(authUserRepository, never()).save(any(AuthUser.class));
    }

    @Test
    @DisplayName("login exitoso devuelve los datos del usuario")
    void login_ok() {
        when(authUserRepository.findByEmail("juan@smartpark.cl"))
                .thenReturn(Optional.of(usuarioExistente));

        AuthResponseDTO resultado = authService.login(loginDTO);

        assertNotNull(resultado);
        assertEquals("Login exitoso", resultado.getMensaje());
        assertEquals("CLIENTE", resultado.getRol());
    }

    @Test
    @DisplayName("login lanza excepción cuando el usuario NO existe")
    void login_usuarioNoExiste_lanzaExcepcion() {
        when(authUserRepository.findByEmail("noexiste@smartpark.cl"))
                .thenReturn(Optional.empty());

        loginDTO.setEmail("noexiste@smartpark.cl");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.login(loginDTO));
        assertEquals("Credenciales invalidas", ex.getMessage());
    }

    @Test
    @DisplayName("login lanza excepción cuando la password es incorrecta")
    void login_passwordIncorrecta_lanzaExcepcion() {
        when(authUserRepository.findByEmail("juan@smartpark.cl"))
                .thenReturn(Optional.of(usuarioExistente));

        // REGLA DE NEGOCIO: password distinta = credenciales inválidas
        loginDTO.setPassword("clave-equivocada");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.login(loginDTO));
        assertEquals("Credenciales invalidas", ex.getMessage());
    }
}
