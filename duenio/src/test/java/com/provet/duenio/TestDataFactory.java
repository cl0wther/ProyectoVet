package com.provet.duenio;

import com.provet.duenio.dto.DuenioRequestDTO;
import com.provet.duenio.model.Duenio;
import net.datafaker.Faker;

/**
 * Fábrica de datos de prueba para el microservicio Dueño.
 */
public class TestDataFactory {

    private static final Faker faker = new Faker();

    // Genera un DTO para peticiones (usado en POST /api/duenio)
    public static DuenioRequestDTO unDuenioRequest() {
        DuenioRequestDTO dto = new DuenioRequestDTO();
        dto.setNombre(faker.name().fullName());
        dto.setCorreo(faker.internet().emailAddress());
        dto.setTelefono(faker.phoneNumber().cellPhone());
        return dto;
    }

    // Genera una Entidad (usado para simular respuestas del repositorio)
    public static Duenio unDuenio() {
        Duenio duenio = new Duenio();
        duenio.setId(faker.number().randomNumber());
        duenio.setNombre(faker.name().fullName());
        duenio.setCorreo(faker.internet().emailAddress());
        duenio.setTelefono(faker.phoneNumber().cellPhone());
        return duenio;
    }
}