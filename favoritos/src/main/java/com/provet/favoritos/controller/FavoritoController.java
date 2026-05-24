package com.provet.favoritos.controller;

import com.provet.favoritos.dto.FavoritoRequestDTO;
import com.provet.favoritos.dto.FavoritoResponseDTO;
import com.provet.favoritos.service.FavoritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Favoritos", description = "Lista de medicamentos favoritos por usuario. Usa WebClient reactivo.")
@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @Operation(summary = "Agregar favorito")
    @PostMapping
    public ResponseEntity<FavoritoResponseDTO> agregar(@Valid @RequestBody FavoritoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoService.agregar(dto));
    }

    @Operation(summary = "Listar todos los favoritos")
    @GetMapping
    public ResponseEntity<List<FavoritoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(favoritoService.listarTodos());
    }

    @Operation(summary = "Listar favoritos de un usuario")
    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<FavoritoResponseDTO>> listarPorUsuario(
            @Parameter(example = "maria") @PathVariable String usuario) {
        return ResponseEntity.ok(favoritoService.listarPorUsuario(usuario));
    }

    @Operation(summary = "Ver qué usuarios tienen un medicamento en favoritos")
    @GetMapping("/medicamento/{medicamentoId}")
    public ResponseEntity<List<FavoritoResponseDTO>> listarPorMedicamento(
            @Parameter(example = "1") @PathVariable Long medicamentoId) {
        return ResponseEntity.ok(favoritoService.listarPorMedicamento(medicamentoId));
    }

    @Operation(summary = "Eliminar favorito")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        favoritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}