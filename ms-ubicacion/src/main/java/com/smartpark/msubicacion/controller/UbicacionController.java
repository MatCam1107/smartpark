package com.smartpark.msubicacion.controller;

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

import com.smartpark.msubicacion.dto.UbicacionRequestDTO;
import com.smartpark.msubicacion.dto.UbicacionResponseDTO;
import com.smartpark.msubicacion.service.UbicacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ubicaciones")
@RequiredArgsConstructor
@Tag(name = "Ubicaciones", description = "Operaciones CRUD sobre ubicaciones")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    @Operation(summary = "Listar ubicaciones", description = "Obtiene la lista completa de ubicaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ubicaciones obtenida correctamente")
    })
    @GetMapping
    public List<UbicacionResponseDTO> obtenerTodas() {
        return ubicacionService.obtenerTodas();
    }

    @Operation(summary = "Obtener ubicacion por id", description = "Obtiene una ubicacion segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ubicacion encontrada"),
            @ApiResponse(responseCode = "404", description = "Ubicacion no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UbicacionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ubicacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear ubicacion", description = "Registra una nueva ubicacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ubicacion creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
    @PostMapping
    public ResponseEntity<UbicacionResponseDTO> crear(
            @Valid @RequestBody UbicacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ubicacionService.guardar(dto));
    }

    @Operation(summary = "Actualizar ubicacion", description = "Actualiza los datos de una ubicacion existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ubicacion actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Ubicacion no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UbicacionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UbicacionRequestDTO dto) {
        return ubicacionService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar ubicacion", description = "Elimina una ubicacion segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ubicacion eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Ubicacion no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ubicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}