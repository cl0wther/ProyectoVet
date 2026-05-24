package com.provet.receta.client;

import com.provet.receta.dto.CitaDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Le decimos a dónde ir a buscar la información de los productos
@FeignClient(name = "cita-service", url = "${cita.service.url}")
public interface CitaClient {

    // Este endpoint debe coincidir con el que tengas en el LibroController/ProductoController de tu catálogo
    @GetMapping("/api/cita/{id}")
    CitaDTO obtenerCitaPorId(@PathVariable("id") Long id);
}