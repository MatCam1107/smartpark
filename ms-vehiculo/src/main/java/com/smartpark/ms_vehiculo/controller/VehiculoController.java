package com.smartpark.ms_vehiculo.controller;

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

import com.smartpark.ms_vehiculo.dto.VehiculoRequestDTO;
import com.smartpark.ms_vehiculo.dto.VehiculoResponseDTO;
import com.smartpark.ms_vehiculo.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
@Tag(name = "Vehiculos", description = "Operaciones CRUD sobre vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @Operation(summary = "Listar vehiculos", description = "Obtiene la lista completa de vehiculos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vehiculos obtenida correctamente")
    })
    @GetMapping
    public List<VehiculoResponseDTO> obtenerTodos() {
        return vehiculoService.obtenerTodos();
    }

    @Operation(summary = "Obtener vehiculo por ID", description = "Obtiene un vehiculo por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehiculo obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return vehiculoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear vehiculo", description = "Crea un nuevo vehiculo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vehiculo creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> crear(
            @Valid @RequestBody VehiculoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculoService.guardar(dto));
    }
    @Operation(summary = "Actualizar vehiculo", description = "Actualiza un vehiculo existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehiculo actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos"),
        @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VehiculoRequestDTO dto) {

        return vehiculoService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar vehiculo", description = "Elimina un vehiculo por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehiculo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}