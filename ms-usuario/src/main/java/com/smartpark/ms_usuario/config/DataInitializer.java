package com.smartpark.ms_usuario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smartpark.ms_usuario.model.Usuario;
import com.smartpark.ms_usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            log.info(">>> Usuarios ya cargados. Se omite inicialización.");
            return;
        }

        log.info(">>> Cargando usuarios iniciales...");

        repository.save(new Usuario(
                null,
                "Andres",
                "Gonzalez",
                "andres@smartpark.cl",
                "987654321",
                "ADMIN",
                true
        ));

        repository.save(new Usuario(
                null,
                "Camila",
                "Rojas",
                "camila@smartpark.cl",
                "912345678",
                "CLIENTE",
                true
        ));

        repository.save(new Usuario(
                null,
                "Felipe",
                "Martinez",
                "felipe@smartpark.cl",
                "998877665",
                "CLIENTE",
                true
        ));

        log.info(">>> 3 usuarios cargados correctamente.");
    }
}