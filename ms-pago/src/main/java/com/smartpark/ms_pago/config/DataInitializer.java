package com.smartpark.ms_pago.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smartpark.ms_pago.model.Pago;
import com.smartpark.ms_pago.model.TipoPago;
import com.smartpark.ms_pago.repository.PagoRepository;
import com.smartpark.ms_pago.repository.TipoPagoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TipoPagoRepository tipoPagoRepository;
    private final PagoRepository pagoRepository;

    @Override
    public void run(String... args) {

        if (tipoPagoRepository.count() == 0) {

            log.info(">>> Cargando tipos de pago iniciales...");

            tipoPagoRepository.save(new TipoPago(null, "Tarjeta Débito", "Pago realizado con tarjeta de débito"));
            tipoPagoRepository.save(new TipoPago(null, "Tarjeta Crédito", "Pago realizado con tarjeta de crédito"));
            tipoPagoRepository.save(new TipoPago(null, "Transferencia", "Pago realizado mediante transferencia bancaria"));

            log.info(">>> Tipos de pago cargados correctamente.");
        }

        if (pagoRepository.count() == 0) {

            log.info(">>> Cargando pagos iniciales...");

            pagoRepository.save(new Pago(null, 1500.0, LocalDate.now(), true, 1L, 1L));
            pagoRepository.save(new Pago(null, 2000.0, LocalDate.now(), true, 2L, 2L));
            pagoRepository.save(new Pago(null, 1200.0, LocalDate.now(), true, 3L, 3L));

            log.info(">>> Pagos cargados correctamente.");
        }
    }
}