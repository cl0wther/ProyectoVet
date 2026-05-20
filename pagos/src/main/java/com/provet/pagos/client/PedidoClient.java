package com.provet.pagos.client;

import com.provet.pagos.dto.PedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Usamos la buena práctica que implementaste de inyectar la URL desde el application.properties
@FeignClient(name = "pedido-service", url = "${pedido.service.url}")
public interface PedidoClient {

    // Este endpoint debe ser el mismo que tienes en tu PedidoController para buscar por ID
    @GetMapping("/api/pedidos/{id}")
    PedidoDTO obtenerPedidoPorId(@PathVariable("id") Long id);

}
