package com.vet.duenio.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.vet.duenio.model.duenio;
import com.vet.duenio.repository.duenioRepository;

@Slf4j

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final duenioRepository duenioRepository;

    @Override
    public void run(String... args) {
        if (duenioRepository.count() > 0) {
            log.info(">>> DataInitializer: Datos de dueños ya existentes, se omite carga.");
            return;
        }

    
        duenioRepository.save(new duenio(null, "Fabian Millar", "fabiann.millar@email.com", "+56912345678"));
        duenioRepository.save(new duenio(null, "Martina Grey", "martina.grey@email.com", "+56987654321"));
        duenioRepository.save(new duenio(null, "Ignacio Retamal", "ignacio.retamal@email.com", "+56945367892"));

        log.info(">>> DataInitializer: {} dueños insertados de prueba con duenioRepository.", duenioRepository.count());
    }
}