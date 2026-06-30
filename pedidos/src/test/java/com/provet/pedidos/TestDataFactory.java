package com.provet.pedidos;

import com.provet.pedidos.dto.MedicamentoDTO;
import com.provet.pedidos.dto.PedidoRequestDTO;
import com.provet.pedidos.model.Pedido;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Fábrica de datos de prueba para el microservicio de Pedidos de Provet.
 */
public class TestDataFactory {

    private static final Faker faker = new Faker();

    public static MedicamentoDTO unMedicamentoDTO() {
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        dto.setNombre(faker.commerce().productName());
        dto.setPrecio(precio());
        // Se añade el stock ya que PedidoService ahora valida la disponibilidad
        dto.setStock(faker.number().numberBetween(10, 100)); 
        dto.setCategoriaNombre(faker.commerce().department());
        return dto;
    }

    public static PedidoRequestDTO unPedidoRequest(Long medicamentoId) {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setMedicamentoId(medicamentoId);
        dto.setUsuario(faker.name().firstName());
        dto.setCantidad(faker.number().numberBetween(1, 5));
        return dto;
    }

    public static Pedido unPedido(MedicamentoDTO medicamento, String usuario, Integer cantidad) {
        Pedido p = new Pedido();
        p.setId(faker.number().numberBetween(1L, 999L));
        p.setMedicamentoId(medicamento.getId());
        p.setNombreMedicamento(medicamento.getNombre());
        p.setPrecioUnitario(medicamento.getPrecio());
        // Calculamos el total para que no falle la aserción p.getTotal().compareTo(ZERO) > 0 en los tests
        p.setTotal(medicamento.getPrecio().multiply(BigDecimal.valueOf(cantidad)));
        p.setUsuario(usuario);
        p.setCantidad(cantidad);
        p.setFechaPedido(LocalDateTime.now());
        return p;
    }

    private static BigDecimal precio() {
        return BigDecimal.valueOf(faker.number().randomDouble(2, 5000, 80000))
                .setScale(2, RoundingMode.HALF_UP);
    }
}