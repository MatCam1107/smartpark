package com.smartpark.ms_usuario.service;

import com.smartpark.ms_usuario.dto.UsuarioRequestDTO;
import com.smartpark.ms_usuario.dto.UsuarioResponseDTO;
import com.smartpark.ms_usuario.model.Usuario;
import com.smartpark.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // ENTITY → RESPONSE DTO
    private UsuarioResponseDTO mapToResponseDTO(Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getRol(),
                usuario.getEstado()
        );
    }

    // REQUEST DTO → ENTITY
    private Usuario mapToEntity(UsuarioRequestDTO dto) {

        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(dto.getRol());
        usuario.setEstado(dto.getEstado());

        return usuario;
    }

    public List<UsuarioResponseDTO> obtenerTodos() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {

        return usuarioRepository.findById(id)
                .map(this::mapToResponseDTO);
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO dto) {

        Usuario usuario = mapToEntity(dto);

        return mapToResponseDTO(
                usuarioRepository.save(usuario)
        );
    }

    public Optional<UsuarioResponseDTO> actualizar(Long id, UsuarioRequestDTO dto) {

        return usuarioRepository.findById(id).map(u -> {

            u.setNombre(dto.getNombre());
            u.setApellido(dto.getApellido());
            u.setEmail(dto.getEmail());
            u.setTelefono(dto.getTelefono());
            u.setRol(dto.getRol());
            u.setEstado(dto.getEstado());

            return mapToResponseDTO(
                    usuarioRepository.save(u)
            );
        });
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}