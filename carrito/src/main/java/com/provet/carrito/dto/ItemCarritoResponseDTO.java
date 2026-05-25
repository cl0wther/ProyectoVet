package com.provet.carrito.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemCarritoResponseDTO {
    private Long id;
    private Long medicamentoId;
    private String nombreMedicamento;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
