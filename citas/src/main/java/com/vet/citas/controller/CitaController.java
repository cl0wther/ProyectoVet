package com.vet.citas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vet.citas.dto.CitaRequestDTO;
import com.vet.citas.dto.CitaResponseDTO;
import com.vet.citas.services.CitaService;

import java.util.List;

@RestController
@RequestMapping("/api/cita")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    
    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return citaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<CitaResponseDTO>> obtenerPorMascota(@PathVariable Long mascotaId) {
        return ResponseEntity.ok(citaService.obtenerPorMascotaId(mascotaId));
    }

    
    @PostMapping
    public ResponseEntity<CitaResponseDTO> crear(@Valid @RequestBody CitaRequestDTO dto) {
        return ResponseEntity.status(201).body(citaService.guardar(dto));
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (citaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}