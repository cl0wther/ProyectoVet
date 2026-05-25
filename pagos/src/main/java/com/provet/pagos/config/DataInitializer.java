package com.provet.pagos.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.provet.pagos.model.Pago;
import com.provet.pagos.repository.PagoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PagoRepository pagoRepository;

    @Override
    public void run(String... args) throws Exception {
        
        if (pagoRepository.count() == 0) {
            
            // Simulamos un pago exitoso
            pagoRepository.save(new Pago(
                null, 1L, new BigDecimal("25990.00"), 
                "TARJETA_CREDITO", "COMPLETADO", LocalDateTime.now(), "TXN-987654"
            ));

            // Simulamos un pago pendiente
            pagoRepository.save(new Pago(
                null, 2L, new BigDecimal("9500.00"), 
                "TRANSFERENCIA", "PENDIENTE", LocalDateTime.now(), null
            ));

            // Simulamos un pago rechazado
            pagoRepository.save(new Pago(
                null, 3L, new BigDecimal("15000.00"), 
                "TARJETA_DEBITO", "RECHAZADO", LocalDateTime.now(), "ERR-001"));
            
            log.info(">>> DataInitializer: {} pagos registrados en el PagoRepository.",
            pagoRepository.count());
        } else {
            log.info(">>> DataInitializer: datos de pago ya ingesados, se omite carga");
            return;
        }
    }
}