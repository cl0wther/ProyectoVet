package com.provet.bodega.client;

import com.provet.bodega.dto.MedicamentoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogo-service", url = "${catalogo.service.url}")
public interface CatalogoClient {

    @GetMapping("/api/catalogo/{id}")
    MedicamentoDTO obtenerMedicamentoPorId(@PathVariable("id") Long id);
}