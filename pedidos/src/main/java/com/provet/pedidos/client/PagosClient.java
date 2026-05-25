package com.provet.pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// Asumiendo que tienes un PagoRequestDTO en tu carpeta de DTOs de Pedidos
import com.provet.pedidos.dto.PagoRequestDTO; 

@FeignClient(name = "pagos-client", url = "${pago.service.url}")
public interface PagosClient {

    // Método para crear el registro de pago
    @PostMapping("/api/pagos")
    Object procesarPago(@RequestBody PagoRequestDTO request);
}