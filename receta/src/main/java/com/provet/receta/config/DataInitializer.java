package com.provet.receta.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.provet.receta.model.DetalleReceta;
import com.provet.receta.model.Receta;
import com.provet.receta.repository.DetalleRepository;
import com.provet.receta.repository.RecetaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RecetaRepository recetaRepository;
    private final DetalleRepository detalleRepository;

    @Override
    public void run(String... args) throws Exception {
        
        if (recetaRepository.count() == 0) {
            
            // 1. Creamos la Receta (id, idMascota, idCita, diagnostico, fechaEmision, fechaVencimiento, estado, detalles)
            Receta receta1 = new Receta();
            receta1.setIdMascota(1L);
            receta1.setIdCita(10L);
            receta1.setDiagnostico("Infección respiratoria leve");
            receta1.setFechaEmision(LocalDate.now());
            receta1.setFechaVencimiento(LocalDate.now().plusDays(30)); // Vence en 30 días
            receta1.setEstado("DISPONIBLE");

            // 2. Creamos los detalles asociados a esta receta
            List<DetalleReceta> detalles = new ArrayList<>();
            
            DetalleReceta detalle1 = new DetalleReceta();
            detalle1.setIdMedicamento(1L); // Amoxicilina
            detalle1.setDosis("1 comprimido cada 12 horas por 7 días");
            detalle1.setReceta(receta1); // ¡Muy importante asociar el detalle a la receta!
            
            detalles.add(detalle1);
            receta1.setDetalles(detalles);

            // 3. Guardamos la receta (el CascadeType.ALL guardará los detalles por nosotros)
            recetaRepository.save(receta1);

            log.info(">>> DataInitializer: {} Recetas y {} detalles ingresadas en el repositorio.",
            recetaRepository.count(), detalleRepository.count());
        }
    }
}
