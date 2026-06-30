package com.smartpark.msubicacion.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smartpark.msubicacion.model.Ubicacion;
import com.smartpark.msubicacion.repository.UbicacionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// ═══════════════════════════════════════════════════
// CommandLineRunner ejecuta run() una vez
// cuando Spring termina de levantar el contexto.
//
// Verificamos count() > 0 para evitar
// duplicar datos al reiniciar.
// ═══════════════════════════════════════════════════
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UbicacionRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            log.info(">>> Ubicaciones ya cargadas. Se omite inicialización.");
            return;
        }

        log.info(">>> Cargando ubicaciones iniciales...");

        repository.save(new Ubicacion(
                null,
                "Av. Providencia 1234",
                "Providencia",
                "Santiago",
                "Metropolitana",
                -33.4253,
                -70.6158
        ));

        repository.save(new Ubicacion(
                null,
                "Av. Apoquindo 4500",
                "Las Condes",
                "Santiago",
                "Metropolitana",
                -33.4120,
                -70.5800
        ));

        repository.save(new Ubicacion(
                null,
                "Av. Libertad 500",
                "Viña del Mar",
                "Viña del Mar",
                "Valparaíso",
                -33.0244,
                -71.5518
        ));

        log.info(">>> 3 ubicaciones cargadas correctamente.");
    }
}