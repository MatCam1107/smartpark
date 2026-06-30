package com.smartpark.ms_revision.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartpark.ms_revision.dto.RevisionRequestDTO;
import com.smartpark.ms_revision.dto.RevisionResponseDTO;
import com.smartpark.ms_revision.service.RevisionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/revisiones")
@RequiredArgsConstructor
@Tag(name = "Revisiones", description = "Operaciones CRUD sobre revisiones")
public class RevisionController {

    private final RevisionService revisionService;

    @Operation(summary = "Listar revisiones", description = "Obtiene la lista completa de revisiones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de revisiones obtenida correctamente")
    })
    @GetMapping
    public List<RevisionResponseDTO> obtenerTodas() {
        return revisionService.obtenerTodas();
    }

    @Operation(summary = "Obtener revision por id", description = "Obtiene una revision segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revision encontrada"),
            @ApiResponse(responseCode = "404", description = "Revision no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RevisionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return revisionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear revision", description = "Registra una nueva revision")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Revision creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
    @PostMapping
    public ResponseEntity<RevisionResponseDTO> crear(
            @Valid @RequestBody RevisionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(revisionService.guardar(dto));
    }

    @Operation(summary = "Actualizar revision", description = "Actualiza los datos de una revision existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revision actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Revision no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RevisionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RevisionRequestDTO dto) {
        return revisionService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar revision", description = "Elimina una revision segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Revision eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Revision no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        revisionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}