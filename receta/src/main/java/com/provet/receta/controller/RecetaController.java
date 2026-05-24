package com.provet.receta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.provet.receta.dto.RecetaRequestDTO;
import com.provet.receta.dto.RecetaResponseDTO;
import com.provet.receta.service.RecetaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
public class RecetaController {

    private final RecetaService recetaService;

    @PostMapping
    public ResponseEntity<RecetaResponseDTO> crearReceta(@RequestBody RecetaRequestDTO requestDTO) {
        log.info("Petición POST recibida en /api/recetas para emisión de receta médica.");
        RecetaResponseDTO nuevaReceta = recetaService.crearReceta(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReceta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> obtenerRecetaPorId(@PathVariable Long id) {
        log.info("Petición GET recibida en /api/recetas/{}", id);
        RecetaResponseDTO receta = recetaService.obtenerPorId(id);
        return ResponseEntity.ok(receta);
    }

    @GetMapping("/validar-compra")
    public ResponseEntity<Boolean> validarRecetaParaCompra(
            @RequestParam("idMascota") Long idMascota,
            @RequestParam("idMedicamento") Long idMedicamento) {
        log.info("Petición de validación interna -> Paciente: {}, Medicamento: {}", idMascota, idMedicamento);
        boolean esValida = recetaService.validarMedicamentoPermitido(idMascota, idMedicamento);
        return ResponseEntity.ok(esValida);
    }
}
