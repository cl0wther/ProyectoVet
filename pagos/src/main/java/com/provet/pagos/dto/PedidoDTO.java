package com.provet.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    
    // Solo traemos lo que a Pagos le interesa saber del Pedido
    private Long id;
    private Long idUsuario; // O idDueño, dependiendo de cómo lo llamaste en Pedidos
    private BigDecimal total; 
    private String estado;  // Para evitar pagar un pedido que ya fue "PAGADO" o "CANCELADO"
    
}