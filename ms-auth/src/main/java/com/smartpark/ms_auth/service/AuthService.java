package com.smartpark.ms_auth.service;

import com.smartpark.ms_auth.dto.AuthResponseDTO;
import com.smartpark.ms_auth.dto.LoginRequestDTO;
import com.smartpark.ms_auth.dto.RegisterRequestDTO;
import com.smartpark.ms_auth.model.AuthUser;
import com.smartpark.ms_auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;

    public AuthResponseDTO registrar(RegisterRequestDTO dto) {

        if (authUserRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya se encuentra registrado");
        }

        AuthUser user = new AuthUser();

        user.setNombre(dto.getNombre());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRol(dto.getRol());

        AuthUser guardado = authUserRepository.save(user);

        return new AuthResponseDTO(
                guardado.getIdAuth(),
                guardado.getNombre(),
                guardado.getEmail(),
                guardado.getRol(),
                "Usuario registrado correctamente"
        );
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        AuthUser user = authUserRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales invalidas"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Credenciales invalidas");
        }

        return new AuthResponseDTO(
                user.getIdAuth(),
                user.getNombre(),
                user.getEmail(),
                user.getRol(),
                "Login exitoso"
        );
    }
}