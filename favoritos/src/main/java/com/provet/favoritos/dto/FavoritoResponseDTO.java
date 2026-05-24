package com.provet.favoritos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FavoritoResponseDTO {
    private Long id;
    private Long medicamentoId;
    private String nombreMedicamento;
    private String usuario;
    private LocalDateTime fechaAgregado;
}