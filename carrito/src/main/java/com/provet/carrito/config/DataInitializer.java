package com.provet.carrito.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.provet.carrito.model.Carrito;
import com.provet.carrito.model.ItemCarrito;
import com.provet.carrito.repository.CarritoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CarritoRepository carritoRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // 1. Verificamos si ya hay carritos para no duplicar datos si reinicias la app
        if (carritoRepository.count() == 0) {
            
            Carrito carrito = new Carrito();
            carrito.setUsuario("101L"); // ID de usuario de prueba
            carrito.setFechaCreacion(LocalDateTime.now());

        
            ItemCarrito item1 = new ItemCarrito(1L, "Amoxicilina", new BigDecimal("8500"), 2, carrito); // 2 unidades de Amoxicilina
            ItemCarrito item2 = new ItemCarrito(3L, "Bravecto", new BigDecimal("25990"), 1, carrito); // 1 unidad de Bravecto
            
            // 3. Creamos el Carrito

            carrito.setItems(Arrays.asList(item1, item2));
            // 4. Guardamos en el repositorio
            // Gracias al CascadeType.ALL, al guardar el Carrito se guardan automáticamente los Items
            carritoRepository.save(carrito);

            log.info(">>> DataInitializer: {} Productos insertados en BodegaRepository.",
            carritoRepository.count());
        }
    }
}
