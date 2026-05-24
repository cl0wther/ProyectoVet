package com.provet.receta.client;

import com.provet.receta.dto.MedicamentoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Le decimos a dónde ir a buscar la información de los productos
@FeignClient(name = "catalogo-service", url = "${catalogo.service.url}")
public interface CatalogoClient {

    // Este endpoint debe coincidir con el que tengas en el LibroController/ProductoController de tu catálogo
    @GetMapping("/api/catalogo/{id}")
    MedicamentoDTO obtenerMedicamentoPorId(@PathVariable("id") Long id);
}