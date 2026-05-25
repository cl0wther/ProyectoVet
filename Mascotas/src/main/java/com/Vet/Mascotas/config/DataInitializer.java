package com.Vet.Mascotas.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Importamos usando tu ruta exacta con mayúsculas
import com.Vet.Mascotas.model.mascota;
import com.Vet.Mascotas.repository.mascotaRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final mascotaRepository mascotaRepository;

    @Override
    public void run(String... args) {
        // Si ya existen mascotas en la base de datos, no hacemos nada para evitar duplicados
        if (mascotaRepository.count() > 0) {
            log.info(">>> DataInitializer Mascotas: Datos ya existentes, se omite la carga inicial.");
            return;
        }

        // Insertamos mascotas de prueba
        // Nota: El último número es el 'duenioId'. Estamos asumiendo que pertenecen al dueño con ID 1 y ID 2.
        mascotaRepository.save(new mascota(null, "Copito", "Perro", "Poodle", 1L));
        mascotaRepository.save(new mascota(null, "Garfield", "Gato", "Pitbull", 1L));
        mascotaRepository.save(new mascota(null, "Luna", "Perro", "Quiltro", 2L));

        log.info(">>> DataInitializer Mascotas: {} mascotas insertadas de prueba correctamente.", mascotaRepository.count());
    }
}