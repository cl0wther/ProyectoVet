package com.provet.favoritos.service;

import com.provet.favoritos.client.CatalogoClient;
import com.provet.favoritos.client.UsuariosClient;
import com.provet.favoritos.dto.FavoritoRequestDTO;
import com.provet.favoritos.dto.FavoritoResponseDTO;
import com.provet.favoritos.dto.MedicamentoDTO;
import com.provet.favoritos.model.Favorito;
import com.provet.favoritos.repository.FavoritoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final CatalogoClient catalogoClient;
    private final UsuariosClient usuariosClient;

    @Transactional
    public FavoritoResponseDTO agregar(FavoritoRequestDTO dto) {
        
        usuariosClient.buscarPorNombre(dto.getUsuario())
                .orElseThrow(() -> new RuntimeException(
                        "El usuario '" + dto.getUsuario() + "' no existe en el sistema"));

        MedicamentoDTO medicamento = catalogoClient.buscarMedicamento(dto.getMedicamentoId())
                .orElseThrow(() -> new RuntimeException(
                        "El medicamento con id " + dto.getMedicamentoId() + " no existe en el catálogo"));

        if (favoritoRepository.existsByMedicamentoIdAndUsuario(dto.getMedicamentoId(), dto.getUsuario())) {
            throw new RuntimeException(
                    "El usuario " + dto.getUsuario() + " ya tiene ese medicamento en favoritos");
        }

        Favorito favorito = new Favorito();
        favorito.setMedicamentoId(dto.getMedicamentoId());
        favorito.setNombreMedicamento(medicamento.getNombre());
        // Se omitió la asignación de laboratorio
        favorito.setUsuario(dto.getUsuario());
        favorito.setFechaAgregado(LocalDateTime.now());

        Favorito guardado = favoritoRepository.save(favorito);
        log.info("Favorito agregado: '{}' para usuario {}", medicamento.getNombre(), dto.getUsuario());
        return toResponse(guardado);
    }

    public List<FavoritoResponseDTO> listarTodos() {
        return favoritoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<FavoritoResponseDTO> listarPorUsuario(String usuario) {
        return favoritoRepository.findByUsuario(usuario).stream().map(this::toResponse).toList();
    }

    public List<FavoritoResponseDTO> listarPorMedicamento(Long medicamentoId) {
        return favoritoRepository.findByMedicamentoId(medicamentoId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public void eliminar(Long id) {
        Favorito favorito = favoritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito con id " + id + " no encontrado"));
        favoritoRepository.delete(favorito);
        log.info("Favorito eliminado: id {}", id);
    }

    private FavoritoResponseDTO toResponse(Favorito favorito) {
        FavoritoResponseDTO dto = new FavoritoResponseDTO();
        dto.setId(favorito.getId());
        dto.setMedicamentoId(favorito.getMedicamentoId());
        dto.setNombreMedicamento(favorito.getNombreMedicamento());
        // Se omitió la obtención de laboratorio
        dto.setUsuario(favorito.getUsuario());
        dto.setFechaAgregado(favorito.getFechaAgregado());
        return dto;
    }
}