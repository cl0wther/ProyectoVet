package com.provet.receta.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleRecetaResponseDTO {
    private Long id;
    private Long idMedicamento;
    private String dosis;
}
