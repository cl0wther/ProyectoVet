package com.provet.bodega.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoDTO {
    
    private Long id;
    private String nombre;
    private String sku; 
    private BigDecimal precio;
    private Boolean requiereReceta;
}