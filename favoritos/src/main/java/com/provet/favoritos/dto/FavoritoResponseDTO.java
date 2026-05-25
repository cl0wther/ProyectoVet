package com.provet.favoritos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoResponseDTO {
    private Long id;
    private Long medicamentoId;
    private String nombreMedicamento;
    private String usuario;
    private LocalDateTime fechaAgregado;
}