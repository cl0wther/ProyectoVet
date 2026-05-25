package com.provet.pedidos.client;

import com.provet.pedidos.dto.MedicamentoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "provet-catalogo", url = "${catalogo.service.url}")
public interface CatalogoClient {

    @GetMapping("/api/medicamentos/{id}")
    MedicamentoDTO obtenerMedicamento(@PathVariable("id") Long id);
}