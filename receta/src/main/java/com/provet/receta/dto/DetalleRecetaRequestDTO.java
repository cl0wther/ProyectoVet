package com.provet.receta.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleRecetaRequestDTO {
    private Long idMedicamento;
    private String dosis;
    private Integer cantidadAutorizada;
}
