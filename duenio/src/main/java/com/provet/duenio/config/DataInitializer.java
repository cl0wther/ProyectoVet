package com.provet.duenio.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.provet.duenio.model.Duenio;
import com.provet.duenio.repository.DuenioRepository;

@Slf4j

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DuenioRepository duenioRepository;

    @Override
    public void run(String... args) {
        if (duenioRepository.count() > 0) {
            log.info(">>> DataInitializer: Datos de dueños ya existentes, se omite carga.");
            return;
        }

    
        duenioRepository.save(new Duenio(null, "Fabian Millar", "fabiann.millar@email.com", "+56912345678"));
        duenioRepository.save(new Duenio(null, "Martina Grey", "martina.grey@email.com", "+56987654321"));
        duenioRepository.save(new Duenio(null, "Ignacio Retamal", "ignacio.retamal@email.com", "+56945367892"));

        log.info(">>> DataInitializer: {} dueños insertados de prueba con duenioRepository.", duenioRepository.count());
    }
}