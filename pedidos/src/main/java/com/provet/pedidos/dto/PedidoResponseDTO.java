package com.provet.pedidos.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PedidoResponseDTO {
    private Long id;
    private String usuario;
    private Long medicamentoId;
    private String nombreMedicamento;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal total;
    private LocalDateTime fechaPedido;
}