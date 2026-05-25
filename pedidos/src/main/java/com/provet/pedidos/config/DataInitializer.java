package com.provet.pedidos.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.provet.pedidos.model.Pedido;
import com.provet.pedidos.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        
        if (pedidoRepository.count() == 0) {
            
            // Constructor sugerido (id, usuario, medicamentoId, nombreMedicamento, cantidad, precioUnitario, total, fechaPedido)
            
            // Pedido 1: Amoxicilina
            pedidoRepository.save(new Pedido(
                null, "Martina", 1L, "Amoxicilina Vet 250mg", 
                2, new BigDecimal("8500"), new BigDecimal("17000"), LocalDateTime.now()
            ));

            // Pedido 2: Bravecto
            pedidoRepository.save(new Pedido(
                null, "Ignacio", 3L, "Bravecto Perros 10-20kg", 
                1, new BigDecimal("25990"), new BigDecimal("25990"), LocalDateTime.now()
            ));
        }
    }
}
