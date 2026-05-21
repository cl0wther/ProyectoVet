package com.Vet.Mascotas.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

// Importamos usando la ruta exacta de tu proyecto (con V y M mayúsculas)
import com.Vet.Mascotas.dto.mascotaRequestDTO;
import com.Vet.Mascotas.dto.mascotaResponseDTO;
import com.Vet.Mascotas.model.mascota;
import com.Vet.Mascotas.repository.mascotaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class mascotasServices {

    private final mascotaRepository mascotaRepository;

    public List<mascotaResponseDTO> obtenerTodas() {
        return mascotaRepository.findAll().stream()
                .map(this::mapToDTO).toList();
    }

    public Optional<mascotaResponseDTO> obtenerPorId(Long id) {
        return mascotaRepository.findById(id).map(this::mapToDTO);
    }

    // Usamos el método extra que creamos en el repositorio
    public List<mascotaResponseDTO> obtenerPorDuenioId(Long duenioId) {
        return mascotaRepository.findByDuenioId(duenioId).stream()
                .map(this::mapToDTO).toList();
    }

    public mascotaResponseDTO guardar(mascotaRequestDTO dto) {
        // Transformamos el DTO al modelo BD
        mascota nuevaMascota = new mascota(null, dto.getNombre(), dto.getEspecie(), dto.getRaza(), dto.getDuenioId());
        return mapToDTO(mascotaRepository.save(nuevaMascota));
    }

    public void eliminar(Long id) {
        mascotaRepository.deleteById(id);
    }

    // Helper para transformar a DTO
    private mascotaResponseDTO mapToDTO(mascota m) {
        return new mascotaResponseDTO(m.getId(), m.getNombre(), m.getEspecie(), m.getRaza(), m.getDuenioId());
    }
}