package com.provet.favoritos.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.provet.favoritos.model.Favorito;
import com.provet.favoritos.repository.FavoritoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final FavoritoRepository favoritoRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // Verificamos si la tabla está vacía para no cargar datos cada vez que reinicias
        if (favoritoRepository.count() == 0) {
            
            
            favoritoRepository.save(new Favorito(null, 1L, "Amoxicilina Vet 250mg", "Martina", LocalDateTime.now()));
            favoritoRepository.save(new Favorito(null, 3L, "Bravecto Perros 10-20kg", "Fabian", LocalDateTime.now()));
            favoritoRepository.save(new Favorito(null, 5L, "Meloxicam Gotas 10ml", "Ignacio", LocalDateTime.now()));

            log.info(">>> DataInitializer: {} favoritos agregados.",
                favoritoRepository.count());
        } else {
            log.info(">>> DataInitializer: datos de favoritos ya agregados, no se genera carga.");
            return;

        }
    }
}
