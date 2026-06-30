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

        PedidoDTO pedido = pedidoClient.obtenerPedidoPorId(request.getIdPedido());
        log.info("Pedido encontrado. Estado actual: {}", pedido.getEstado());

        if (request.getMontoTotal().compareTo(pedido.getTotal()) != 0) {
            log.error("Alerta de fraude/error: El cliente intentó pagar {} pero el total real es {}", 
                      request.getMontoTotal(), pedido.getTotal());
            throw new RuntimeException("El monto ingresado no coincide con el total del pedido.");
        }

        boolean transaccionAprobada = Math.random() <= 0.8;
        String estadoFinal = transaccionAprobada ? "COMPLETADO" : "RECHAZADO";

        Pago pago = new Pago();
        pago.setIdPedido(request.getIdPedido());
        pago.setMontoTotal(request.getMontoTotal());
        pago.setMetodoPago(request.getMetodoPago().toUpperCase());
        pago.setEstado(estadoFinal);
        pago.setFechaPago(LocalDateTime.now());
        

        pago.setCodigoTransaccion(UUID.randomUUID().toString().substring(0, 8).toUpperCase());


        Pago pagoGuardado = pagoRepository.save(pago);
        
        if (transaccionAprobada) {
            log.info("¡Pago exitoso! Transacción {} guardada.", pagoGuardado.getCodigoTransaccion());

        } else {
            log.warn("Pago RECHAZADO para el pedido {} por fondos insuficientes.", request.getIdPedido());
        }


        return mapearAResponse(pagoGuardado);
    }

    @Transactional(readOnly = true)
    public PagoResponseDTO obtenerPagoPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún pago con el ID: " + id));
        return mapearAResponse(pago);
    }


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
