package com.smartpark.ms_estacionamiento.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smartpark.ms_estacionamiento.model.Estacionamiento;
import com.smartpark.ms_estacionamiento.repository.EstacionamientoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EstacionamientoRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            log.info(">>> Estacionamientos ya cargados. Se omite inicialización.");
            return;
        }

        log.info(">>> Cargando estacionamientos iniciales...");

        repository.save(new Estacionamiento(
                null,
                "Parking Center Providencia",
                "Estacionamiento techado cercano al metro",
                1500.0,
                50,
                true,
                1L,
                2L
        ));

        repository.save(new Estacionamiento(
                null,
                "SmartPark Las Condes",
                "Estacionamiento privado con acceso controlado",
                2000.0,
                80,
                true,
                2L,
                2L
        ));

        repository.save(new Estacionamiento(
                null,
                "Parking Viña Centro",
                "Estacionamiento abierto en zona céntrica",
                1200.0,
                40,
                true,
                3L,
                2L
        ));

        log.info(">>> 3 estacionamientos cargados correctamente.");
    }
}