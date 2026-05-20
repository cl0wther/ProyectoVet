package com.provet.pagos.controller;

import com.provet.pagos.dto.PagoRequestDTO;
import com.provet.pagos.dto.PagoResponseDTO;
import com.provet.pagos.service.PagoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    // 1. Endpoint para procesar un nuevo pago
    @PostMapping
    public ResponseEntity<PagoResponseDTO> procesarPago(@RequestBody PagoRequestDTO request) {
        log.info("Petición REST recibida para procesar pago del Pedido N° {}", request.getIdPedido());
        
        PagoResponseDTO respuesta = pagoService.procesarPago(request);
        
        // Evaluamos el estado final que decidió el Service
        if ("RECHAZADO".equals(respuesta.getEstado())) {
            log.warn("Retornando HTTP 402 - Pago Rechazado");
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(respuesta);
        }
        
        log.info("Retornando HTTP 201 - Pago Exitoso");
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // 2. Endpoint para obtener un comprobante histórico
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerComprobante(@PathVariable Long id) {
        log.info("Petición REST para obtener comprobante de pago ID: {}", id);
        PagoResponseDTO comprobante = pagoService.obtenerPagoPorId(id);
        return ResponseEntity.ok(comprobante);
    }
}
