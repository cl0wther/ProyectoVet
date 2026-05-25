package com.provet.carrito.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MedicamentoDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private String categoriaNombre;
}
