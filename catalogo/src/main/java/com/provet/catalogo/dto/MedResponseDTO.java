package com.provet.catalogo.dto;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedResponseDTO {
    private Long id;
    private String nombreMed;
    private BigDecimal precio;
    private String descripcion;
    private String categoriaNombre;


}
