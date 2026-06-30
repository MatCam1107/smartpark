package com.smartpark.ms_admin.controller;

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

import com.smartpark.ms_admin.dto.AdminRequestDTO;
import com.smartpark.ms_admin.dto.AdminResponseDTO;
import com.smartpark.ms_admin.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Tag(name = "Administradores", description = "Operaciones CRUD sobre administradores")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Listar administradores", description = "Obtiene la lista completa de administradores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida correctamente")
    })
    @GetMapping
    public List<AdminResponseDTO> obtenerTodos() {
        return adminService.obtenerTodos();
    }

    @Operation(summary = "Obtener administrador por id", description = "Obtiene un administrador según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> obtenerPorId(@PathVariable Long id) {
        return adminService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear administrador", description = "Registra un nuevo administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<AdminResponseDTO> crear(@Valid @RequestBody AdminRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminService.guardar(dto));
    }

    @Operation(summary = "Actualizar administrador", description = "Actualiza los datos de un administrador existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AdminRequestDTO dto) {

        return adminService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar administrador", description = "Elimina un administrador según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Administrador eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        adminService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}