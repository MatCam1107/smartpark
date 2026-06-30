package com.smartpark.ms_admin.service;

import com.smartpark.ms_admin.dto.AdminRequestDTO;
import com.smartpark.ms_admin.dto.AdminResponseDTO;
import com.smartpark.ms_admin.model.Admin;
import com.smartpark.ms_admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private AdminResponseDTO convertirADTO(Admin admin) {
        return new AdminResponseDTO(
                admin.getIdAdmin(),
                admin.getNombre(),
                admin.getEmail(),
                admin.getAccion(),
                admin.getFechaAccion(),
                admin.getUsuarioId()
        );
    }

    private Admin convertirAEntidad(AdminRequestDTO dto) {
        Admin admin = new Admin();

        admin.setNombre(dto.getNombre());
        admin.setEmail(dto.getEmail());
        admin.setAccion(dto.getAccion());
        admin.setFechaAccion(dto.getFechaAccion());
        admin.setUsuarioId(dto.getUsuarioId());

        return admin;
    }

    public List<AdminResponseDTO> obtenerTodos() {
        return adminRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Optional<AdminResponseDTO> obtenerPorId(Long id) {
        return adminRepository.findById(id)
                .map(this::convertirADTO);
    }

    public AdminResponseDTO guardar(AdminRequestDTO dto) {
        Admin admin = convertirAEntidad(dto);
        Admin guardado = adminRepository.save(admin);
        return convertirADTO(guardado);
    }

    public Optional<AdminResponseDTO> actualizar(Long id, AdminRequestDTO dto) {
        return adminRepository.findById(id).map(a -> {
            a.setNombre(dto.getNombre());
            a.setEmail(dto.getEmail());
            a.setAccion(dto.getAccion());
            a.setFechaAccion(dto.getFechaAccion());
            a.setUsuarioId(dto.getUsuarioId());

            Admin actualizado = adminRepository.save(a);
            return convertirADTO(actualizado);
        });
    }

    public void eliminar(Long id) {
        adminRepository.deleteById(id);
    }
}