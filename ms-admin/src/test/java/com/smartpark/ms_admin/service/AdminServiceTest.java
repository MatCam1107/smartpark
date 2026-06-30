package com.smartpark.ms_admin.service;

import com.smartpark.ms_admin.dto.AdminRequestDTO;
import com.smartpark.ms_admin.dto.AdminResponseDTO;
import com.smartpark.ms_admin.model.Admin;
import com.smartpark.ms_admin.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Prueba unitaria del servicio. Usa Mockito para simular el repositorio
// (no toca la base de datos real). @ExtendWith activa Mockito en JUnit 5.
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    // @Mock crea un repositorio "falso" controlado por nosotros.
    @Mock
    private AdminRepository adminRepository;

    // @InjectMocks crea el servicio real e inyecta el mock de arriba.
    @InjectMocks
    private AdminService adminService;

    private Admin admin;
    private AdminRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // Datos de ejemplo reutilizables en cada prueba.
        admin = new Admin(
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
    @DisplayName("obtenerTodos devuelve la lista de administradores")
    void obtenerTodos_devuelveLista() {
        // GIVEN: el repositorio devuelve un admin
        when(adminRepository.findAll()).thenReturn(List.of(admin));

        // WHEN: pedimos todos
        List<AdminResponseDTO> resultado = adminService.obtenerTodos();

        // THEN: viene 1 elemento con los datos correctos
        assertEquals(1, resultado.size());
        assertEquals("Carlos Soto", resultado.get(0).getNombre());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerPorId devuelve el admin cuando existe")
    void obtenerPorId_existe() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        Optional<AdminResponseDTO> resultado = adminService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("carlos@smartpark.cl", resultado.get().getEmail());
        verify(adminRepository).findById(1L);
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío cuando NO existe")
    void obtenerPorId_noExiste() {
        when(adminRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<AdminResponseDTO> resultado = adminService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("guardar persiste y devuelve el admin creado")
    void guardar_creaAdmin() {
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        AdminResponseDTO resultado = adminService.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals("Carlos Soto", resultado.getNombre());
        assertEquals(1L, resultado.getIdAdmin());
        verify(adminRepository).save(any(Admin.class));
    }

    @Test
    @DisplayName("actualizar modifica el admin cuando existe")
    void actualizar_existe() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Optional<AdminResponseDTO> resultado = adminService.actualizar(1L, requestDTO);

        assertTrue(resultado.isPresent());
        assertEquals("Carlos Soto", resultado.get().getNombre());
        verify(adminRepository).findById(1L);
        verify(adminRepository).save(any(Admin.class));
    }

    @Test
    @DisplayName("actualizar devuelve vacío cuando el admin NO existe")
    void actualizar_noExiste() {
        when(adminRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<AdminResponseDTO> resultado = adminService.actualizar(99L, requestDTO);

        assertTrue(resultado.isEmpty());
        // si no existe, nunca se debe llamar a save
        verify(adminRepository, never()).save(any(Admin.class));
    }

    @Test
    @DisplayName("eliminar invoca deleteById del repositorio")
    void eliminar_llamaRepositorio() {
        doNothing().when(adminRepository).deleteById(1L);

        adminService.eliminar(1L);

        verify(adminRepository, times(1)).deleteById(1L);
    }
}
