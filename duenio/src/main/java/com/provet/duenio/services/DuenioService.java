package com.provet.duenio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.provet.duenio.dto.DuenioRequestDTO;
import com.provet.duenio.dto.DuenioResponseDTO;
import com.provet.duenio.model.Duenio;
import com.provet.duenio.repository.DuenioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DuenioService {

    private final DuenioRepository duenioRepository;

    public List<DuenioResponseDTO> obtenerTodos() {
        return duenioRepository.findAll().stream()
                .map(this::mapToDTO) 
                .toList(); 
    }


    public Optional<DuenioResponseDTO> obtenerPorId(Long id) {
        return duenioRepository.findById(id).map(this::mapToDTO);
    }


    public DuenioResponseDTO guardar(DuenioRequestDTO dto) {
        Duenio nuevoDuenio = new Duenio(null, dto.getNombre(), dto.getCorreo(), dto.getTelefono());
        
        Duenio duenioGuardado = duenioRepository.save(nuevoDuenio);
        
        return mapToDTO(duenioGuardado);
    }

    public void eliminar(Long id) {
        duenioRepository.deleteById(id);
    }

    private DuenioResponseDTO mapToDTO(Duenio d) {
        return new DuenioResponseDTO(d.getId(), d.getNombre(), d.getCorreo(), d.getTelefono());
    }
}