package com.provet.bodega.controller;


import com.provet.bodega.dto.AjusteStockDTO;
import com.provet.bodega.dto.InventarioRequestDTO;
import com.provet.bodega.dto.InventarioResponseDTO;
import com.provet.bodega.service.InventarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    // 1. Registrar un producto nuevo en bodega
    @PostMapping
    public ResponseEntity<InventarioResponseDTO> registrarProducto(@RequestBody InventarioRequestDTO dto) {
        log.info("Recibida solicitud para registrar nuevo producto en bodega: ID {}", dto.getIdProducto());
        return ResponseEntity.status(201)
                             .body(inventarioService.registrarProducto(dto));
    }

    // 2. Descontar stock (Este endpoint lo llamará Pedidos/Pagos tras una venta)
    @PostMapping("/ajustar-stock")
    public ResponseEntity<String> ajustarStock(@RequestBody AjusteStockDTO ajuste) {
        log.info("Ajustando stock para producto ID {}: {} unidades", ajuste.getIdProducto(), ajuste.getCantidad());
        inventarioService.procesarVenta(ajuste);
        return ResponseEntity.ok("Stock actualizado correctamente en la bodega.");
    }

    // 3. Consultar stock actual de un producto
    @GetMapping("/{idProducto}")
    public ResponseEntity<InventarioResponseDTO> consultarStock(@PathVariable Long idProducto) {
        return ResponseEntity.ok(inventarioService.consultarStockPorProducto(idProducto));
    }

    // 4. Reporte crítico para el administrador
    @GetMapping("/criticos")
    public ResponseEntity<List<InventarioResponseDTO>> obtenerCriticos() {
        return ResponseEntity.ok(inventarioService.obtenerProductosEnStockCritico());
    }
}