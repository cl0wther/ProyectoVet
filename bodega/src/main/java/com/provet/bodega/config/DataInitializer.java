package com.provet.bodega.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.provet.bodega.model.Inventario;
import com.provet.bodega.repository.InventarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) {
        if (inventarioRepository.count() > 0) {
            log.info(">>> DataInitializer: datos de bodega ya existentes, se omite carga.");
            return;
        
        }

        inventarioRepository.save(new Inventario(null, 1L, 50, 10, "Pasillo A-01", LocalDateTime.now()));
        inventarioRepository.save(new Inventario(null, 2L, 30, 5, "Pasillo A-02", LocalDateTime.now()));
        inventarioRepository.save(new Inventario(null, 3L, 20, 5, "Pasillo B-01", LocalDateTime.now()));
        inventarioRepository.save(new Inventario(null, 4L, 40, 8, "Pasillo B-02", LocalDateTime.now()));
        inventarioRepository.save(new Inventario(null, 5L, 25, 5, "Pasillo C-01", LocalDateTime.now()));
        inventarioRepository.save(new Inventario(null, 6L, 15, 3, "Pasillo C-02", LocalDateTime.now()));
        inventarioRepository.save(new Inventario(null, 7L, 100, 20, "Pasillo D-01", LocalDateTime.now()));
        inventarioRepository.save(new Inventario(null, 8L, 60, 10, "Pasillo D-02", LocalDateTime.now()));
        inventarioRepository.save(new Inventario(null, 9L, 10, 2, "Pasillo E-01", LocalDateTime.now()));

        log.info(">>> DataInitializer: {} Productos insertados en BodegaRepository.",
        inventarioRepository.count());
    }
}
