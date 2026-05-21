package com.vet.duenio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vet.duenio.dto.duenioRequestDTO;
import com.vet.duenio.dto.duenioResponseDTO;
import com.vet.duenio.model.duenio;
import com.vet.duenio.repository.duenioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class duenioServices {

    private final duenioRepository duenioRepository;

    public List<duenioResponseDTO> obtenerTodos() {
        return duenioRepository.findAll().stream()
                .map(this::mapToDTO) 
                .toList(); 
    }


    public Optional<duenioResponseDTO> obtenerPorId(Long id) {
        return duenioRepository.findById(id).map(this::mapToDTO);
    }


    public duenioResponseDTO guardar(duenioRequestDTO dto) {
        duenio nuevoDuenio = new duenio(null, dto.getNombre(), dto.getCorreo(), dto.getTelefono());
        
        duenio duenioGuardado = duenioRepository.save(nuevoDuenio);
        
        return mapToDTO(duenioGuardado);
    }

    public void eliminar(Long id) {
        duenioRepository.deleteById(id);
    }

    private duenioResponseDTO mapToDTO(duenio d) {
        return new duenioResponseDTO(d.getId(), d.getNombre(), d.getCorreo(), d.getTelefono());
    }
}