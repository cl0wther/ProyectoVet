package com.provet.bodega.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioRequestDTO {
    private Long idProducto;
    private Integer stockInicial; 
    private Integer stockMinimo;  
    private String ubicacion;     
}