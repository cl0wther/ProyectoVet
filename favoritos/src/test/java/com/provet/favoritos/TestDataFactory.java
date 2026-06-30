package com.provet.favoritos;

import com.provet.favoritos.dto.FavoritoRequestDTO;
import com.provet.favoritos.dto.MedicamentoDTO;
import com.provet.favoritos.dto.UsuarioDTO;
import com.provet.favoritos.model.Favorito;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Fábrica de datos de prueba para el microservicio de Favoritos de Provet.
 */
public class TestDataFactory {

    private static final Faker faker = new Faker();

    public static MedicamentoDTO unMedicamentoDTO() {
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        // Genera un nombre de producto aleatorio para simular un medicamento
        dto.setNombre(faker.commerce().productName()); 
        dto.setStock(faker.number().numberBetween(10, 100));
        dto.setPrecio(BigDecimal.valueOf(faker.number().randomDouble(2, 5000, 80000))
                .setScale(2, RoundingMode.HALF_UP));
        dto.setCategoriaNombre(faker.commerce().department());
        return dto;
    }

    public static UsuarioDTO unUsuarioDTO() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        dto.setNombre(faker.name().firstName());
        dto.setCorreo(faker.internet().emailAddress());
        return dto;
    }

    public static FavoritoRequestDTO unFavoritoRequest(Long medicamentoId, String usuario) {
        FavoritoRequestDTO dto = new FavoritoRequestDTO();
        dto.setMedicamentoId(medicamentoId);
        dto.setUsuario(usuario);
        return dto;
    }

    public static Favorito unFavorito(Long medicamentoId, String nombreMedicamento, String usuario) {
        Favorito f = new Favorito();
        f.setId(faker.number().numberBetween(1L, 999L));
        f.setMedicamentoId(medicamentoId);
        f.setNombreMedicamento(nombreMedicamento);
        f.setUsuario(usuario);
        f.setFechaAgregado(LocalDateTime.now());
        return f;
    }
}