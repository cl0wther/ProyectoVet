package com.provet.favoritos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.provet.favoritos.dto.UsuarioDTO;

@FeignClient(name = "duenio-service", url = "${duenio.service.url}")
public interface UsuariosClient {

    // Asumiendo que el MS de mascota tiene un endpoint estándar de búsqueda por ID
    @GetMapping("/api/mascotas/{id}")
    UsuarioDTO obtenerDuenioPorNombre(@PathVariable("nombre") String nombre);
}

