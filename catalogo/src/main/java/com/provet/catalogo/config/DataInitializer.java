package com.provet.catalogo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.provet.catalogo.model.CategoriaMed;
import com.provet.catalogo.model.Medicamento;
import com.provet.catalogo.repository.CategoriaRepository;
import com.provet.catalogo.repository.MedRepository;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final MedRepository productoRepository;

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() > 0) {
            log.info(">>> DataInitializer: datos farmacéuticos ya existentes, se omite carga.");
            return;
        }

        // 1. Crear las categorías de la farmacia veterinaria
        CategoriaMed antibioticos = categoriaRepository.save(new CategoriaMed(null, "Antibióticos", "Tratamiento de infecciones bacterianas"));
        CategoriaMed antiparasitarios = categoriaRepository.save(new CategoriaMed(null, "Antiparasitarios", "Control de parásitos internos y externos"));
        CategoriaMed analgesicos = categoriaRepository.save(new CategoriaMed(null, "Analgésicos", "Manejo del dolor y la inflamación"));
        CategoriaMed suplementos = categoriaRepository.save(new CategoriaMed(null, "Suplementos", "Apoyo nutricional y vitamínico"));

// 2. Insertar los medicamentos usando tu medRepository
        // Nota: Asegúrate de que el orden de los parámetros coincida con el constructor de tu modelo
        medRepository.save(new Medicamento(null, "Amoxicilina Vet 250mg", "MED-ANT-001", new BigDecimal("8500"), true, antibioticos));
        medRepository.save(new Medicamento(null, "Doxiciclina 100mg", "MED-ANT-002", new BigDecimal("12000"), true, antibioticos));

        medRepository.save(new Medicamento(null, "Bravecto Perros 10-20kg", "MED-PAR-001", new BigDecimal("25990"), false, antiparasitarios));
        medRepository.save(new Medicamento(null, "Milbemax Gatos", "MED-PAR-002", new BigDecimal("9500"), false, antiparasitarios));

        medRepository.save(new Medicamento(null, "Meloxicam Gotas 10ml", "MED-ANA-001", new BigDecimal("15000"), true, analgesicos));
        medRepository.save(new Medicamento(null, "Carprofeno 50mg", "MED-ANA-002", new BigDecimal("18500"), true, analgesicos));

        medRepository.save(new Medicamento(null, "Omega 3 Mascotas 60 caps", "MED-SUP-001", new BigDecimal("14000"), false, suplementos));
        medRepository.save(new Medicamento(null, "Probióticos Digestivos", "MED-SUP-002", new BigDecimal("11500"), false, suplementos));

        log.info(">>> DataInitializer: {} categorías y {} medicamentos insertados con MedRepository.",
                categoriaRepository.count(), medRepository.count());
}
