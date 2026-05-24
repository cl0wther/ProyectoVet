package com.provet.favoritos.client;

import com.provet.favoritos.dto.MedicamentoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogoClient {

    private final WebClient catalogoWebClient;

    public Optional<MedicamentoDTO> buscarMedicamento(Long medicamentoId) {
        try {
            MedicamentoDTO medicamento = catalogoWebClient.get()
                    .uri("/api/medicamentos/{id}", medicamentoId)
                    .retrieve()
                    .bodyToMono(MedicamentoDTO.class)
                    .block();
            return Optional.ofNullable(medicamento);
        } catch (WebClientResponseException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al conectar con provet_catalogo: {}", e.getMessage());
            throw new RuntimeException("El servicio de catálogo no está disponible");
        }
    }
}