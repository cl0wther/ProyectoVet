package com.provet.mascota.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.provet.mascota.dto.DuenioDTO;

@FeignClient(name = "duenio-service", url = "${duenio.service.url}")
public interface DuenioClient {

    // Asumiendo que el MS de mascota tiene un endpoint estándar de búsqueda por ID
    @GetMapping("/api/mascotas/{id}")
    DuenioDTO obtenerMascotaPorNombre(@PathVariable("nombre") String nombre);
}
