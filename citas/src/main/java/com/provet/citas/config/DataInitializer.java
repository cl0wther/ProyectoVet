package com.provet.citas.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.provet.citas.model.Cita;
import com.provet.citas.repository.CitaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CitaRepository citaRepository;

    @Override
    public void run(String... args) {
        if (citaRepository.count() > 0) {
            log.info(">>> DataInitializer Citas: Datos ya existentes, saltando inicialización.");
            return;
        }

        // Insertamos citas de prueba (asociadas a la mascota ID 1 y 2 que creamos en el otro microservicio)
        citaRepository.save(new Cita(null, 1L, LocalDate.now().plusDays(1), LocalTime.of(10, 30), "Control de vacuna anual"));
        citaRepository.save(new Cita(null, 1L, LocalDate.now().plusDays(7), LocalTime.of(15, 00), "Revisión por otitis"));
        citaRepository.save(new Cita(null, 2L, LocalDate.now().plusDays(2), LocalTime.of(11, 15), "Peluquería y corte de uñas"));

        log.info(">>> DataInitializer Citas: {} citas de prueba insertadas con éxito.", citaRepository.count());
    }
}