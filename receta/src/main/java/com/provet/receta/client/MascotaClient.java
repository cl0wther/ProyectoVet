package com.provet.receta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.provet.receta.dto.MascotaDTO;

@FeignClient(name = "mascota-service", url = "${mascota.service.url}")
public interface MascotaClient {

    // Asumiendo que el MS de mascota tiene un endpoint estándar de búsqueda por ID
    @GetMapping("/api/mascotas/{id}")
    MascotaDTO obtenerMascotaPorId(@PathVariable("id") Long id);

}