package com.provet.carrito;

import com.provet.carrito.dto.ItemCarritoRequestDTO;
import com.provet.carrito.dto.MedicamentoDTO;
import com.provet.carrito.model.Carrito;
import com.provet.carrito.model.ItemCarrito;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Fábrica de datos de prueba para el microservicio de Carrito Provet.
 */
public class TestDataFactory {

    private static final Faker faker = new Faker();

    public static MedicamentoDTO unMedicamentoDTO() {
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        // Genera un nombre aleatorio que simula ser un producto/fármaco
        dto.setNombre(faker.commerce().productName()); 
        dto.setStock(faker.number().numberBetween(10, 100));
        dto.setPrecio(precio());
        dto.setCategoriaNombre(faker.commerce().department());
        return dto;
    }

    public static Carrito unCarrito() {
        return new Carrito(faker.name().firstName());
    }

    public static ItemCarrito unItem(MedicamentoDTO medicamento, Carrito carrito) {
        return new ItemCarrito(
                medicamento.getId(),
                medicamento.getNombre(),
                medicamento.getPrecio(),
                faker.number().numberBetween(1, 5),
                carrito
        );
    }

    public static ItemCarritoRequestDTO unItemRequest(Long medicamentoId) {
        ItemCarritoRequestDTO dto = new ItemCarritoRequestDTO();
        dto.setMedicamentoId(medicamentoId);
        dto.setCantidad(faker.number().numberBetween(1, 5));
        return dto;
    }

    private static BigDecimal precio() {
        return BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 50000))
                .setScale(2, RoundingMode.HALF_UP);
    }
}