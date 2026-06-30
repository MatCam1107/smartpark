package com.smartpark.ms_revision.service;

import com.smartpark.ms_revision.dto.RevisionResponseDTO;
import com.smartpark.ms_revision.model.Revision;
import com.smartpark.ms_revision.repository.RevisionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Prueba unitaria del servicio de revisiones con Mockito.
// Se prueban las operaciones de lectura/borrado (no usan WebClient).
@ExtendWith(MockitoExtension.class)
class RevisionServiceTest {

    @Mock
    private RevisionRepository revisionRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private RevisionService revisionService;

    private Revision revision;

    @BeforeEach
    void setUp() {
        revision = new Revision(
                1L, 5, "Excelente lugar", LocalDate.of(2025, 6, 10), 3L, 2L
        );
    }

    @Test
    @DisplayName("obtenerTodas devuelve la lista de revisiones")
    void obtenerTodas_devuelveLista() {
        when(revisionRepository.findAll()).thenReturn(List.of(revision));

        List<RevisionResponseDTO> resultado = revisionService.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getCalificacion());
        verify(revisionRepository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId devuelve la revisión cuando existe")
    void obtenerPorId_existe() {
        when(revisionRepository.findById(1L)).thenReturn(Optional.of(revision));

        Optional<RevisionResponseDTO> resultado = revisionService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Excelente lugar", resultado.get().getComentario());
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío cuando NO existe")
    void obtenerPorId_noExiste() {
        when(revisionRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<RevisionResponseDTO> resultado = revisionService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("eliminar invoca deleteById")
    void eliminar_llamaRepositorio() {
        doNothing().when(revisionRepository).deleteById(1L);

        revisionService.eliminar(1L);

        verify(revisionRepository, times(1)).deleteById(1L);
    }
}
