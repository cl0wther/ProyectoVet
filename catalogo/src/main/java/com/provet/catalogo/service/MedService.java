package com.provet.catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.provet.catalogo.dto.MedRequestDTO;
import com.provet.catalogo.dto.MedResponseDTO;
import com.provet.catalogo.model.CategoriaMed;
import com.provet.catalogo.model.Medicamento;
import com.provet.catalogo.repository.CategoriaRepository;
import com.provet.catalogo.repository.MedRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedService {

    private final MedRepository medRepository;
    private final CategoriaRepository categoriaRepository;

    public List<MedResponseDTO> obtenerTodos(){
        return medRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public Optional<MedResponseDTO> obtenerPorId(Long id){
        log.info("Consulta medicamento id={}", id);
        return medRepository.findById(id).map(this::mapToDTO);
    }

    public MedResponseDTO guardar (MedRequestDTO dto){
        CategoriaMed cat = categoriaRepository.findById(dto.getCategoriaId())
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada con Id " + dto.getCategoriaId()));
        Medicamento med = new Medicamento(null, dto.getNombreMed(), dto.getPrecio(), dto.getDescripcion(), cat);
        return mapToDTO(medRepository.save(med));
    }

    public void eliminar(Long id){
        medRepository.deleteById(id);
    }

    private MedResponseDTO mapToDTO (Medicamento m){
        return new MedResponseDTO(m.getId(), m.getNombreMed(), m.getPrecioMed(), 
            m.getDescripcion(), m.getCategoria().getNombre());
    }
}
