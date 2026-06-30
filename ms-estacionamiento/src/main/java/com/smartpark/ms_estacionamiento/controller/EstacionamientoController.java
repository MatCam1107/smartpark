package com.smartpark.ms_estacionamiento.controller;

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

import com.smartpark.ms_estacionamiento.dto.EstacionamientoRequestDTO;
import com.smartpark.ms_estacionamiento.dto.EstacionamientoResponseDTO;
import com.smartpark.ms_estacionamiento.service.EstacionamientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estacionamientos")
@RequiredArgsConstructor
@Tag(name = "Estacionamientos", description = "Operaciones CRUD sobre estacionamientos")
public class EstacionamientoController {

    private final EstacionamientoService estacionamientoService;

    @Operation(summary = "Listar estacionamientos", description = "Obtiene la lista completa de estacionamientos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estacionamientos obtenida correctamente")
    })
    @GetMapping
    public List<EstacionamientoResponseDTO> obtenerTodos() {
        return estacionamientoService.obtenerTodos();
    }

    @Operation(summary = "Obtener estacionamiento por id", description = "Obtiene un estacionamiento según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estacionamiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Estacionamiento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstacionamientoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return estacionamientoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear estacionamiento", description = "Registra un nuevo estacionamiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estacionamiento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<EstacionamientoResponseDTO> crear(
            @Valid @RequestBody EstacionamientoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estacionamientoService.guardar(dto));
    }

    @Operation(summary = "Actualizar estacionamiento", description = "Actualiza los datos de un estacionamiento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estacionamiento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Estacionamiento no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstacionamientoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstacionamientoRequestDTO dto) {

        return estacionamientoService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar estacionamiento", description = "Elimina un estacionamiento según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estacionamiento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Estacionamiento no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        estacionamientoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}