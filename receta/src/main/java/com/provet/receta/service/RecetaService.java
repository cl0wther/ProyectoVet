package com.provet.receta.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.provet.receta.client.CatalogoClient;
import com.provet.receta.client.CitaClient;
import com.provet.receta.client.MascotaClient;
import com.provet.receta.dto.*;
import com.provet.receta.model.DetalleReceta;
import com.provet.receta.model.Receta;
import com.provet.receta.repository.RecetaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository recetaRepository;
    
    // Inyección de los tres clientes HTTP declarativos
    private final MascotaClient mascotaClient;
    private final CatalogoClient catalogoClient;
    private final CitaClient citaClient;

    @Transactional
    public RecetaResponseDTO crearReceta(RecetaRequestDTO requestDTO) {
        log.info("Iniciando validaciones entre microservicios para la nueva receta...");

        // 1. Validar externamente que la Mascota exista
        MascotaDTO mascota = mascotaClient.obtenerMascotaPorId(requestDTO.getIdMascota());
        log.info("Paciente validado correctamente: {}", mascota.getNombre());

        // 2. Validar externamente que la Cita exista
        CitaDTO cita = citaClient.obtenerCitaPorId(requestDTO.getIdCita());
        log.info("Cita médica validada correctamente. Fecha atención: {}", cita.getFechaHora());

        // 3. Crear y configurar la entidad base de la Receta
        Receta receta = new Receta();
        receta.setIdMascota(requestDTO.getIdMascota());
        receta.setIdCita(requestDTO.getIdCita());
        receta.setDiagnostico(requestDTO.getDiagnostico());
        receta.setFechaVencimiento(requestDTO.getFechaVencimiento());
        receta.setFechaEmision(LocalDate.now());
        receta.setEstado("DISPONIBLE");

        // 4. Transformar y validar cada medicamento en la lista de detalles
        List<DetalleReceta> detalles = requestDTO.getDetalles().stream().map(dto -> {
            log.info("Validando medicamento ID: {} en el catálogo", dto.getIdMedicamento());
            
            // Validar externamente que el medicamento exista en el catálogo
            MedicamentoDTO medicamento = catalogoClient.obtenerMedicamentoPorId(dto.getIdMedicamento());
            

            DetalleReceta detalle = new DetalleReceta();
            detalle.setIdMedicamento(medicamento.getId());
            detalle.setDosis(dto.getDosis());
            detalle.setReceta(receta); // Mantiene el vínculo bidireccional de la base de datos
            
            return detalle;
        }).collect(Collectors.toList());

        receta.setDetalles(detalles);

        // 5. Guardar en la base de datos (con persistencia en cascada de los detalles)
        Receta recetaGuardada = recetaRepository.save(receta);
        log.info("Receta N° {} guardada con éxito en la base de datos.", recetaGuardada.getId());

        return mapearAResponseDTO(recetaGuardada);
    }

    @Transactional(readOnly = true)
    public RecetaResponseDTO obtenerPorId(Long id) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con el ID: " + id));
        return mapearAResponseDTO(receta);
    }

    @Transactional(readOnly = true)
    public boolean validarMedicamentoPermitido(Long idMascota, Long idMedicamento) {
        // Ejecuta la consulta personalizada que cruza cabecera y detalles en la BD
        List<Receta> recetasValidas = recetaRepository.buscarRecetasValidasParaCompra(idMascota, idMedicamento);
        return !recetasValidas.isEmpty();
    }

    // --- MAPEO DE ENTIDAD A RESPONSE DTO ---
    private RecetaResponseDTO mapearAResponseDTO(Receta receta) {
        List<DetalleRecetaResponseDTO> detallesDTO = receta.getDetalles().stream()
                .map(d -> new DetalleRecetaResponseDTO(
                        d.getId(),
                        d.getIdMedicamento(),
                        d.getDosis()
                )).collect(Collectors.toList());

        return new RecetaResponseDTO(
                receta.getId(),
                receta.getIdMascota(),
                receta.getIdCita(),
                receta.getDiagnostico(),
                receta.getFechaEmision(),
                receta.getFechaVencimiento(),
                receta.getEstado(),
                detallesDTO
        );
    }
}
