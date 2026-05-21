package com.Vet.Mascotas.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Importamos usando tu ruta exacta con mayúsculas
import com.Vet.Mascotas.dto.mascotaRequestDTO;
import com.Vet.Mascotas.dto.mascotaResponseDTO;
import com.Vet.Mascotas.services.mascotasServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mascota")
@RequiredArgsConstructor
public class mascotaController {

    // Inyectamos tu servicio (fíjate que le puse la 's' al final para que coincida con tu archivo mascotasServices.java)
    private final mascotasServices mascotasServices;

    // Ruta: GET http://localhost:8084/api/mascota
    @GetMapping
    public ResponseEntity<List<mascotaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(mascotasServices.obtenerTodas());
    }

    // Ruta: GET http://localhost:8084/api/mascota/{id}
    @GetMapping("/{id}")
    public ResponseEntity<mascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return mascotasServices.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ruta: GET http://localhost:8084/api/mascota/duenio/{duenioId}
    @GetMapping("/duenio/{duenioId}")
    public ResponseEntity<List<mascotaResponseDTO>> obtenerPorDuenio(@PathVariable Long duenioId) {
        return ResponseEntity.ok(mascotasServices.obtenerPorDuenioId(duenioId));
    }

    // Ruta: POST http://localhost:8084/api/mascota
    @PostMapping
    public ResponseEntity<mascotaResponseDTO> crear(@Valid @RequestBody mascotaRequestDTO dto) {
        return ResponseEntity.status(201).body(mascotasServices.guardar(dto));
    }

    // Ruta: DELETE http://localhost:8084/api/mascota/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (mascotasServices.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mascotasServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}