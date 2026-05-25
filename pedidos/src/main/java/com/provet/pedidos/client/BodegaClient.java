package com.provet.pedidos.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "bodega-client", url = "${bodega.service.url}")
public interface BodegaClient {

    // Método 1: Preguntar si hay stock suficiente ANTES de vender
    @GetMapping("/api/inventario/verificar/{idProducto}")
    Boolean verificarStock(@PathVariable("idProducto") Long idProducto, @RequestParam("cantidad") Integer cantidad);

    // Método 2: Ordenar a la bodega que reste el stock DESPUÉS de vender
    @PutMapping("/api/inventario/descontar/{idProducto}")
    void descontarStock(@PathVariable("idProducto") Long idProducto, @RequestParam("cantidad") Integer cantidad);
}