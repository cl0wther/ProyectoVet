package com.provet.bodega;

import net.datafaker.Faker;
import java.time.LocalDateTime;

import com.provet.bodega.dto.InventarioRequestDTO;
import com.provet.bodega.model.Inventario;

public class TestDataFactory {

    private static final Faker faker = new Faker();

    public static Inventario unInventario() {
        Inventario inv = new Inventario();
        inv.setId(faker.number().numberBetween(1L, 999L));
        inv.setIdProducto(faker.number().numberBetween(1L, 999L));
        
        inv.setStockActual(faker.number().numberBetween(50, 500));
        inv.setStockMinimo(faker.number().numberBetween(10, 30));
        
        inv.setUbicacion("Pasillo " + faker.number().numberBetween(1, 15) + ", Estante " + faker.letterify("?").toUpperCase());

        inv.setUltimaReposicion(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30)));
        
        return inv;
    }

    public static InventarioRequestDTO unInventarioRequest() {
        InventarioRequestDTO dto = new InventarioRequestDTO();
        dto.setIdProducto(faker.number().numberBetween(1L, 999L));
        dto.setStockInicial(faker.number().numberBetween(50, 500));
        dto.setStockMinimo(faker.number().numberBetween(10, 30));
        
        dto.setUbicacion("Bodega Central - Estante " + faker.number().numberBetween(1, 20));
        
        return dto;
    }
}