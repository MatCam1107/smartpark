package com.smartpark.ms_pago.controller;

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

import com.smartpark.ms_pago.dto.TipoPagoRequestDTO;
import com.smartpark.ms_pago.dto.TipoPagoResponseDTO;
import com.smartpark.ms_pago.service.TipoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tipos-pago")
@RequiredArgsConstructor
@Tag(name = "Tipos de Pago", description = "Operaciones CRUD sobre tipos de pago")
public class TipoPagoController {

    private final TipoPagoService tipoPagoService;

    @Operation(summary = "Listar tipos de pago", description = "Obtiene la lista completa de tipos de pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de pago obtenida correctamente")
    })
    @GetMapping
    public List<TipoPagoResponseDTO> obtenerTodos() {
        return tipoPagoService.obtenerTodos();
    }

    @Operation(summary = "Obtener tipo de pago por id", description = "Obtiene un tipo de pago según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoPagoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return tipoPagoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear tipo de pago", description = "Registra un nuevo tipo de pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<TipoPagoResponseDTO> crear(@Valid @RequestBody TipoPagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipoPagoService.guardar(dto));
    }

    @Operation(summary = "Actualizar tipo de pago", description = "Actualiza los datos de un tipo de pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de pago actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de pago no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TipoPagoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TipoPagoRequestDTO dto) {

        return tipoPagoService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar tipo de pago", description = "Elimina un tipo de pago según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipoPagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}