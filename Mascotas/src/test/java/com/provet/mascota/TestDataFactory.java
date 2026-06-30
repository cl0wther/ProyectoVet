package com.provet.mascota;

import com.provet.mascota.dto.MascotaRequestDTO;
import com.provet.mascota.model.Mascotas;
import net.datafaker.Faker;

public class TestDataFactory {

    private static final Faker faker = new Faker();

    public static MascotaRequestDTO unaMascotaRequest() {
        MascotaRequestDTO dto = new MascotaRequestDTO();
        dto.setNombre(faker.animal().name());
        dto.setEspecie(faker.animal().genus());
        dto.setRaza(faker.dog().breed());
        dto.setDuenioId(faker.number().randomNumber());
        return dto;
    }

    public static Mascotas unaMascota() {
        Mascotas mascota = new Mascotas();
        mascota.setId(faker.number().randomNumber());
        mascota.setNombre(faker.animal().name());
        mascota.setEspecie(faker.animal().genus());
        mascota.setRaza(faker.dog().breed());
        mascota.setDuenioId(faker.number().randomNumber());
        return mascota;
    }
}