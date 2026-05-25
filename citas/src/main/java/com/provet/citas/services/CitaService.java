package com.provet.citas.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.provet.citas.dto.CitaRequestDTO;
import com.provet.citas.dto.CitaResponseDTO;
import com.provet.citas.model.Cita;
import com.provet.citas.repository.CitaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;

    public List<CitaResponseDTO> obtenerTodas() {
        return citaRepository.findAll().stream()
                .map(this::mapToDTO).toList();
    }

    public Optional<CitaResponseDTO> obtenerPorId(Long id) {
        return citaRepository.findById(id).map(this::mapToDTO);
    }

    public List<CitaResponseDTO> obtenerPorMascotaId(Long mascotaId) {
        return citaRepository.findByMascotaId(mascotaId).stream()
                .map(this::mapToDTO).toList();
    }

    public CitaResponseDTO guardar(CitaRequestDTO dto) {
        Cita nuevaCita = new Cita(null, dto.getMascotaId(), dto.getFecha(), dto.getHora(), dto.getMotivo());
        return mapToDTO(citaRepository.save(nuevaCita));
    }

    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    private CitaResponseDTO mapToDTO(Cita c) {
        return new CitaResponseDTO(c.getId(), c.getMascotaId(), c.getFecha(), c.getHora(), c.getMotivo());
    }
}