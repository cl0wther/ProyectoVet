package com.provet.receta.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoDTO {
    private Long id;
    private String nombreMed;
    private BigDecimal precio;
    private String descripcion;
    private String categoriaNombre;

}
