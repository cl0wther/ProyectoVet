package com.provet.pedidos.client;

import com.provet.pedidos.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-client", url = "${duenio.service.url}")
public interface UsuariosClient {

    @GetMapping("/api/usuarios/nombre/{nombre}")
    UsuarioDTO obtenerUsuarioPorNombre(@PathVariable("nombre") String nombre);
}