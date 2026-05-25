package com.provet.duenio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provet.duenio.dto.DuenioRequestDTO;
import com.provet.duenio.dto.DuenioResponseDTO;
import com.provet.duenio.services.DuenioService;

// Importamos ambos DTOs para utilizarlos en los parámetros y respuestas de las rutas web


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/duenio")
@RequiredArgsConstructor
public class DuenioController {


    private final DuenioService duenioServices;


    @GetMapping
    public ResponseEntity<List<DuenioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(duenioServices.obtenerTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<DuenioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return duenioServices.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<DuenioResponseDTO> crear(@Valid @RequestBody DuenioRequestDTO dto) {
        return ResponseEntity.status(201).body(duenioServices.guardar(dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (duenioServices.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        duenioServices.eliminar(id);
        
        return ResponseEntity.noContent().build();
    }
}