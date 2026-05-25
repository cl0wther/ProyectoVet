package com.provet.mascota.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.provet.mascota.dto.MascotaRequestDTO;
import com.provet.mascota.dto.MascotaResponseDTO;
import com.provet.mascota.service.MascotaService;

// Importamos usando tu ruta exacta con mayúsculas
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mascota")
@RequiredArgsConstructor
public class MascotaController {

    // Inyectamos tu servicio (fíjate que le puse la 's' al final para que coincida con tu archivo mascotasServices.java)
    private final MascotaService mascotaService;

    // Ruta: GET http://localhost:8084/api/mascota
    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(mascotaService.obtenerTodas());
    }

    // Ruta: GET http://localhost:8084/api/mascota/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return mascotaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ruta: GET http://localhost:8084/api/mascota/duenio/{duenioId}
    @GetMapping("/duenio/{duenioId}")
    public ResponseEntity<List<MascotaResponseDTO>> obtenerPorDuenio(@PathVariable Long duenioId) {
        return ResponseEntity.ok(mascotaService.obtenerPorDuenioId(duenioId));
    }

    // Ruta: POST http://localhost:8084/api/mascota
    @PostMapping
    public ResponseEntity<MascotaResponseDTO> crear(@Valid @RequestBody MascotaRequestDTO dto) {
        return ResponseEntity.status(201).body(mascotaService.guardar(dto));
    }

    // Ruta: DELETE http://localhost:8084/api/mascota/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (mascotaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}