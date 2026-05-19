package com.provet.catalogo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provet.catalogo.dto.MedRequestDTO;
import com.provet.catalogo.dto.MedResponseDTO;
import com.provet.catalogo.service.MedService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/medicamento")
@RequiredArgsConstructor
public class MedController {

    private final MedService medService;

    @GetMapping
    public ResponseEntity<List<MedResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(medService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedResponseDTO> obtenerPorId(@PathVariable Long id){
        return medService.obtenerPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MedResponseDTO> crear (@Valid @RequestBody MedRequestDTO dto){
        return ResponseEntity.status(201).body(medService.guardar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        if (medService.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        medService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    }
    
    

