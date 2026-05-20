package com.provet.catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.provet.catalogo.model.CategoriaMed;
import com.provet.catalogo.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public List<CategoriaMed> obtenerTodas() { return categoriaRepository.findAll(); }
    public Optional<CategoriaMed> obtenerPorId(Long id) { return categoriaRepository.findById(id);}
    public CategoriaMed guardar (CategoriaMed cMed) { return categoriaRepository.save(cMed);}
    public void eliminar (Long id) {categoriaRepository.deleteById(id); }
    

}
