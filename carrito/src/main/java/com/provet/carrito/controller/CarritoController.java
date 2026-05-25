package com.provet.carrito.controller;

import com.provet.carrito.dto.CarritoResponseDTO;
import com.provet.carrito.dto.ItemCarritoRequestDTO;
import com.provet.carrito.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    // POST /api/carritos?usuario=nombreUsuario
    @PostMapping
    public ResponseEntity<CarritoResponseDTO> crear(@RequestParam String usuario) {
        return ResponseEntity.status(201).body(carritoService.crear(usuario));
    }

    // GET /api/carritos/5
    @GetMapping("/{id}")
    public ResponseEntity<CarritoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return carritoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/carritos?usuario=nombreUsuario
    @GetMapping
    public ResponseEntity<List<CarritoResponseDTO>> obtenerPorUsuario(@RequestParam String usuario) {
        return ResponseEntity.ok(carritoService.obtenerPorUsuario(usuario));
    }

    // POST /api/carritos/5/items
    @PostMapping("/{id}/items")
    public ResponseEntity<CarritoResponseDTO> agregarItem(@PathVariable Long id,
                                                          @Valid @RequestBody ItemCarritoRequestDTO dto) {
        // Llama a agregarItem pasando el ID de la URL y el DTO del cuerpo de la petición
        return ResponseEntity.ok(carritoService.agregarItem(id, dto));
    }

    // DELETE /api/carritos/5/items/12
    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<CarritoResponseDTO> quitarItem(@PathVariable Long id,
                                                         @PathVariable Long itemId) {
        return ResponseEntity.ok(carritoService.quitarItem(id, itemId));
    }

    // DELETE /api/carritos/5/vaciar
    @DeleteMapping("/{id}/vaciar")
    public ResponseEntity<CarritoResponseDTO> vaciar(@PathVariable Long id) {
        return ResponseEntity.ok(carritoService.vaciar(id));
    }

    // DELETE /api/carritos/5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (carritoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        carritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}