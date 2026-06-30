package com.provet.pagos;

import net.datafaker.Faker;

import com.provet.pagos.dto.PagoRequestDTO;
import com.provet.pagos.dto.PagoResponseDTO;
import com.provet.pagos.dto.PedidoDTO;
import com.provet.pagos.model.Pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestDataFactory {

    private static final Faker faker = new Faker();

    public static Pago unPago() {
        Pago pago = new Pago();
        pago.setId(faker.number().numberBetween(1L, 999L));
        pago.setIdPedido(faker.number().numberBetween(1L, 999L));
        
        // Generamos un monto aleatorio entre 10.00 y 500.00
        pago.setMontoTotal(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)));
        
        // faker.options() elige una de las opciones al azar
        pago.setMetodoPago(faker.options().option("TARJETA_CREDITO", "TARJETA_DEBITO", "TRANSFERENCIA"));
        pago.setEstado(faker.options().option("PENDIENTE", "COMPLETADO", "RECHAZADO"));
        
        // Simula que el pago se hizo en las últimas 24 horas (hasta 1440 minutos atrás)
        pago.setFechaPago(LocalDateTime.now().minusMinutes(faker.number().numberBetween(1, 1440)));
        
        // Genera un código de transacción único y realista
        pago.setCodigoTransaccion(UUID.randomUUID().toString());
        
        return pago;
    }

    public static PagoRequestDTO unPagoRequest() {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setIdPedido(faker.number().numberBetween(1L, 999L));
        dto.setMontoTotal(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)));
        dto.setMetodoPago(faker.options().option("TARJETA_CREDITO", "TARJETA_DEBITO", "TRANSFERENCIA"));
        
        return dto;
    }

    public static PagoResponseDTO unPagoResponse() {
        PagoResponseDTO dto = new PagoResponseDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        dto.setIdPedido(faker.number().numberBetween(1L, 999L));
        dto.setMontoTotal(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)));
        dto.setMetodoPago(faker.options().option("TARJETA_CREDITO", "TARJETA_DEBITO", "TRANSFERENCIA"));
        dto.setEstado(faker.options().option("PENDIENTE", "COMPLETADO", "RECHAZADO"));
        dto.setFechaPago(LocalDateTime.now().minusMinutes(faker.number().numberBetween(1, 1440)));
        dto.setCodigoTransaccion(UUID.randomUUID().toString());
        
        return dto;
    }

    // Este es muy útil para cuando tengas que mockear la respuesta de un FeignClient hacia el microservicio de Pedidos
    public static PedidoDTO unPedidoDTO() {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(faker.number().numberBetween(1L, 999L));
        dto.setIdUsuario(faker.number().numberBetween(1L, 999L));
        dto.setTotal(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 500)));
        
        // Por lo general, si estamos intentando pagar un pedido, asumimos que viene como PENDIENTE
        dto.setEstado("PENDIENTE"); 
        
        return dto;
    }
}