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
    private final MedRepository medRepository;

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
        CategoriaMed ansiolitico = categoriaRepository.save(new CategoriaMed(null, "Ansiolitico", " medicamentos psicotrópicos que actúan sobre el sistema nervioso central para reducir los síntomas de ansiedad, temor, tensión muscular e insomnio"));

        // 2. Insertar los medicamentos usando medRepository
        medRepository.save(new Medicamento(null, "Amoxicilina Vet 250mg", new BigDecimal("8500"), "antibiótico de amplio espectro derivado de la penicilina", antibioticos));
        medRepository.save(new Medicamento(null, "Doxiciclina 100mg", new BigDecimal("12000"), "antibiótico de amplio espectro de la familia de las tetraciclinas", antibioticos));

        medRepository.save(new Medicamento(null, "Bravecto Perros 10-20kg", new BigDecimal("25990"), "antiparasitario externo para perros y gatos que elimina pulgas, garrapatas y ciertos tipos de ácaros", antiparasitarios));
        medRepository.save(new Medicamento(null, "Milbemax Gatos", new BigDecimal("9500"), "antiparasitario interno oral de amplio espectro", antiparasitarios));

        medRepository.save(new Medicamento(null, "Meloxicam Gotas 10ml", new BigDecimal("15000"), "medicamento antiinflamatorio no esteroideo (AINE) utilizado para aliviar el dolor", analgesicos));
        medRepository.save(new Medicamento(null, "Carprofeno 50mg", new BigDecimal("18500"), "fármaco antiinflamatorio no esteroideo (AINE) ", analgesicos));

        medRepository.save(new Medicamento(null, "Omega 3 Mascotas 60 caps", new BigDecimal("14000"), "es un suplemento esencial para los perros que aporta grandes", suplementos));
        medRepository.save(new Medicamento(null, "Probióticos Digestivos", new BigDecimal("11500"), "microorganismos vivos que equilibran la microbiota intestinal, mejorando la digestión, la absorción de nutrientes y reforzando el sistema inmune", suplementos));

        medRepository.save(new Medicamento(null, "Fluoxetina", new BigDecimal("3000"), "Juanin es muy feliz con este", ansiolitico));

        log.info(">>> DataInitializer: {} categorías y {} medicamentos insertados con MedRepository.",
                categoriaRepository.count(), medRepository.count());
    }
}
