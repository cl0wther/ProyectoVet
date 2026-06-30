package com.provet.receta;

import net.datafaker.Faker;

import com.provet.receta.dto.*;
import com.provet.receta.model.DetalleReceta;
import com.provet.receta.model.Receta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestDataFactory {

    private static final Faker faker = new Faker();

    public static Receta unaReceta() {
        Receta receta = new Receta();
        receta.setId(faker.number().numberBetween(1L, 999L));
        receta.setIdMascota(faker.number().numberBetween(1L, 999L));
        receta.setIdCita(faker.number().numberBetween(1L, 999L));
        receta.setDiagnostico(faker.options().option("Infección respiratoria", "Gastroenteritis", "Otitis", "Dermatitis"));
        receta.setFechaEmision(LocalDate.now());
        receta.setFechaVencimiento(LocalDate.now().plusDays(faker.number().numberBetween(15, 30)));
        receta.setEstado(faker.options().option("DISPONIBLE", "UTILIZADA", "VENCIDA"));
        
        DetalleReceta detalle = unDetalleReceta();
        detalle.setReceta(receta);
        receta.setDetalles(List.of(detalle));

        return receta;
    }

    public static DetalleReceta unDetalleReceta() {
        DetalleReceta detalle = new DetalleReceta();
        detalle.setId(faker.number().numberBetween(1L, 999L));
        detalle.setIdMedicamento(faker.number().numberBetween(1L, 999L));
        detalle.setDosis("1 comprimido cada " + faker.options().option("8", "12", "24") + " horas");
        return detalle;
    }

    public static RecetaRequestDTO unaRecetaRequest() {
        RecetaRequestDTO dto = new RecetaRequestDTO();
        dto.setIdMascota(faker.number().numberBetween(1L, 999L));
        dto.setIdCita(faker.number().numberBetween(1L, 999L));
        dto.setDiagnostico(faker.options().option("Infección respiratoria", "Gastroenteritis", "Otitis", "Dermatitis"));
        dto.setFechaVencimiento(LocalDate.now().plusDays(30));
        dto.setDetalles(List.of(unDetalleRecetaRequest()));
        return dto;
    }

    public static DetalleRecetaRequestDTO unDetalleRecetaRequest() {
        DetalleRecetaRequestDTO dto = new DetalleRecetaRequestDTO();
        dto.setIdMedicamento(faker.number().numberBetween(1L, 999L));
        dto.setDosis("1 comprimido cada 12 horas");
        dto.setCantidadAutorizada(faker.number().numberBetween(1, 5));
        return dto;
    }

    public static RecetaResponseDTO unaRecetaResponse() {
        RecetaResponseDTO dto = new RecetaResponseDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        dto.setIdMascota(faker.number().numberBetween(1L, 999L));
        dto.setIdCita(faker.number().numberBetween(1L, 999L));
        dto.setDiagnostico(faker.options().option("Infección respiratoria", "Gastroenteritis", "Otitis", "Dermatitis"));
        dto.setFechaEmision(LocalDate.now());
        dto.setFechaVencimiento(LocalDate.now().plusDays(30));
        dto.setEstado("DISPONIBLE");
        dto.setDetalles(List.of(unDetalleRecetaResponse()));
        return dto;
    }

    public static DetalleRecetaResponseDTO unDetalleRecetaResponse() {
        DetalleRecetaResponseDTO dto = new DetalleRecetaResponseDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        dto.setIdMedicamento(faker.number().numberBetween(1L, 999L));
        dto.setDosis("1 comprimido cada 12 horas");
        return dto;
    }

    public static CitaDTO unaCitaDTO() {
        CitaDTO dto = new CitaDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        dto.setFechaHora(LocalDateTime.now().minusHours(1));
        dto.setMotivo(faker.options().option("Fiebre y letargo", "Vómitos", "Control de rutina", "Falta de apetito"));
        dto.setIdMascota(faker.number().numberBetween(1L, 999L));
        return dto;
    }

    public static MascotaDTO unaMascotaDTO() {
        MascotaDTO dto = new MascotaDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        dto.setNombre(faker.dog().name());
        dto.setEspecie(faker.animal().name());
        dto.setIdDueno(faker.number().numberBetween(1L, 999L));
        return dto;
    }

    public static MedicamentoDTO unMedicamentoDTO() {
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        dto.setNombreMed(faker.options().option("Amoxicilina", "Meloxicam", "Ivermectina", "Prednisona"));
        dto.setPrecio(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 100)));
        dto.setDescripcion("Medicamento de uso veterinario");
        dto.setCategoriaNombre(faker.options().option("Antibiótico", "Analgésico", "Antiinflamatorio"));
        return dto;
    }
}