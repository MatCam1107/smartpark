package com.smartpark.ms_usuario.service;

import com.smartpark.ms_usuario.dto.UsuarioRequestDTO;
import com.smartpark.ms_usuario.dto.UsuarioResponseDTO;
import com.smartpark.ms_usuario.model.Usuario;
import com.smartpark.ms_usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Prueba unitaria del servicio de usuarios. Mockito simula el repositorio,
// por lo que NO se necesita base de datos para correr estas pruebas.
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(
                1L, "Juan", "Perez", "juan@smartpark.cl",
                "+56912345678", "CLIENTE", true
        );

        requestDTO = new UsuarioRequestDTO(
                "Juan", "Perez", "juan@smartpark.cl",
                "+56912345678", "CLIENTE", true
        );
    }

    @Test
    @DisplayName("obtenerTodos devuelve la lista de usuarios")
    void obtenerTodos_devuelveLista() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioResponseDTO> resultado = usuarioService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        verify(usuarioRepository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId devuelve el usuario cuando existe")
    void obtenerPorId_existe() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<UsuarioResponseDTO> resultado = usuarioService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("juan@smartpark.cl", resultado.get().getEmail());
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío cuando NO existe")
    void obtenerPorId_noExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UsuarioResponseDTO> resultado = usuarioService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("guardar crea y devuelve el usuario")
    void guardar_creaUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO resultado = usuarioService.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals(1L, resultado.getIdUsuario());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("actualizar modifica el usuario cuando existe")
    void actualizar_existe() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Optional<UsuarioResponseDTO> resultado = usuarioService.actualizar(1L, requestDTO);

        assertTrue(resultado.isPresent());
        assertEquals("Perez", resultado.get().getApellido());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("actualizar devuelve vacío cuando NO existe")
    void actualizar_noExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UsuarioResponseDTO> resultado = usuarioService.actualizar(99L, requestDTO);

        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_llamaRepositorio() {
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.eliminar(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
