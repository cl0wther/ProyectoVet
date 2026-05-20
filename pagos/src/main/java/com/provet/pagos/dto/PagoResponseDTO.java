package com.provet.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponseDTO {
    
    
    private Long id;
    private Long idPedido;
    private BigDecimal montoTotal;
    private String metodoPago;
    private String estado;
    private LocalDateTime fechaPago;
    private String codigoTransaccion; 
}
