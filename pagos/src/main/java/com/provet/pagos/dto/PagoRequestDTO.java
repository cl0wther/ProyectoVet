package com.provet.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {
    
    private Long idPedido;      
    private BigDecimal montoTotal; 
    private String metodoPago;

}