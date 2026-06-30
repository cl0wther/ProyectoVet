package com.provet.citas;


import net.datafaker.Faker;
import java.time.LocalDate;
import java.time.LocalTime;

import com.provet.citas.dto.CitaRequestDTO;
import com.provet.citas.model.Cita;

public class TestDataFactory {
    private static final Faker faker = new Faker();

    public static CitaRequestDTO unaCitaRequest() {
        CitaRequestDTO dto = new CitaRequestDTO();
        dto.setMascotaId(faker.number().randomNumber());
        dto.setFecha(LocalDate.now().plusDays(1));
        dto.setHora(LocalTime.of(10, 0));
        dto.setMotivo("Chequeo general");
        return dto;
    }

    public static Cita unaCita() {
        return new Cita(1L, 1L, LocalDate.now().plusDays(1), LocalTime.of(10, 0), "Chequeo general");
    }
}