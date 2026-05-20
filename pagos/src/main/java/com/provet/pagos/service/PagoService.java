package com.provet.pagos.service;

import com.provet.pagos.client.PedidoClient;
import com.provet.pagos.dto.PagoRequestDTO;
import com.provet.pagos.dto.PagoResponseDTO;
import com.provet.pagos.dto.PedidoDTO;
import com.provet.pagos.model.Pago;
import com.provet.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final PedidoClient pedidoClient;

    @Transactional
    public PagoResponseDTO procesarPago(PagoRequestDTO request) {
        log.info("Iniciando intento de pago para el Pedido N° {}", request.getIdPedido());

        // 1. Ir a buscar la verdad absoluta al microservicio de Pedidos
        PedidoDTO pedido = pedidoClient.obtenerPedidoPorId(request.getIdPedido());
        log.info("Pedido encontrado. Estado actual: {}", pedido.getEstado());

        // 2. Validar que nadie esté intentando pagar de menos alterando los datos en el frontend
        // Usamos compareTo() porque en Java los BigDecimal no se comparan con "=="
        if (request.getMontoTotal().compareTo(pedido.getTotal()) != 0) {
            log.error("Alerta de fraude/error: El cliente intentó pagar {} pero el total real es {}", 
                      request.getMontoTotal(), pedido.getTotal());
            throw new RuntimeException("El monto ingresado no coincide con el total del pedido.");
        }

        // 3. Simular la pasarela de pagos (Transbank/Webpay)
        // Math.random() da un valor entre 0.0 y 1.0. Si es menor o igual a 0.8, pasa.
        boolean transaccionAprobada = Math.random() <= 0.8;
        String estadoFinal = transaccionAprobada ? "COMPLETADO" : "RECHAZADO";

        // 4. Armar el registro para la base de datos
        Pago pago = new Pago();
        pago.setIdPedido(request.getIdPedido());
        pago.setMontoTotal(request.getMontoTotal());
        pago.setMetodoPago(request.getMetodoPago().toUpperCase());
        pago.setEstado(estadoFinal);
        pago.setFechaPago(LocalDateTime.now());
        
        // Generamos un código alfanumérico aleatorio de 8 caracteres tipo "A4F8B9C2"
        pago.setCodigoTransaccion(UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // 5. Guardar la transacción
        Pago pagoGuardado = pagoRepository.save(pago);
        
        if (transaccionAprobada) {
            log.info("¡Pago exitoso! Transacción {} guardada.", pagoGuardado.getCodigoTransaccion());
            // NOTA FUTURA: Aquí podrías disparar un evento asíncrono para que el 
            // microservicio de Pedidos cambie su estado de "PENDIENTE" a "PAGADO".
        } else {
            log.warn("Pago RECHAZADO para el pedido {} por fondos insuficientes.", request.getIdPedido());
        }

        // 6. Retornar el comprobante
        return mapearAResponse(pagoGuardado);
    }

    @Transactional(readOnly = true)
    public PagoResponseDTO obtenerPagoPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún pago con el ID: " + id));
        return mapearAResponse(pago);
    }

    // --- MÉTODOS DE APOYO ---
    private PagoResponseDTO mapearAResponse(Pago pago) {
        return new PagoResponseDTO(
                pago.getId(),
                pago.getIdPedido(),
                pago.getMontoTotal(),
                pago.getMetodoPago(),
                pago.getEstado(),
                pago.getFechaPago(),
                pago.getCodigoTransaccion()
        );
    }
}
