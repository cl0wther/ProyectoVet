package com.provet.mascota.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

// Importamos usando la ruta exacta de tu proyecto (con V y M mayúsculas)
import com.provet.mascota.dto.MascotaRequestDTO;
import com.provet.mascota.dto.MascotaResponseDTO;
import com.provet.mascota.model.Mascotas;
import com.provet.mascota.repository.MascotaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public List<MascotaResponseDTO> obtenerTodas() {
        return mascotaRepository.findAll().stream()
                .map(this::mapToDTO).toList();
    }

    public Optional<MascotaResponseDTO> obtenerPorId(Long id) {
        return mascotaRepository.findById(id).map(this::mapToDTO);
    }

    // Usamos el método extra que creamos en el repositorio
    public List<MascotaResponseDTO> obtenerPorDuenioId(Long duenioId) {
        return mascotaRepository.findByDuenioId(duenioId).stream()
                .map(this::mapToDTO).toList();
    }

    public MascotaResponseDTO guardar(MascotaRequestDTO dto) {
        // Transformamos el DTO al modelo BD
        Mascotas nuevaMascota = new Mascotas(null, dto.getNombre(), dto.getEspecie(), dto.getRaza(), dto.getDuenioId());
        return mapToDTO(mascotaRepository.save(nuevaMascota));
    }

    public void eliminar(Long id) {
        mascotaRepository.deleteById(id);
    }

    // Helper para transformar a DTO
    private MascotaResponseDTO mapToDTO(Mascotas m) {
        return new MascotaResponseDTO(m.getId(), m.getNombre(), m.getEspecie(), m.getRaza(), m.getDuenioId());
    }
}