package com.provet.carrito.client;

import com.provet.carrito.dto.MedicamentoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * CatalogoClient
 *
 * Clínica Veterinaria Provet
 *
 * Esta interfaz representa la conexión HTTP hacia el microservicio provet_catalogo.
 * Cuando CarritoService llama a obtenerMedicamento(id), FeignClient genera automáticamente:
 * GET ${catalogo.service.url}/api/medicamentos/{id}
 *
 * Si provet_catalogo no está corriendo o el medicamento no existe, lanzará una FeignException.
 */
@FeignClient(name = "provet-catalogo", url = "${catalogo.service.url}")
public interface CatalogoClient {

    @GetMapping("/api/medicamento/{id}")
    MedicamentoDTO obtenerMedicamento(@PathVariable Long id);
}