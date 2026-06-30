package com.provet.bodega.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioResponseDTO {
    private Long id;
    private Long idProducto;
    private Integer stockActual;
    private Integer stockMinimo;
    private String ubicacion;
    private LocalDateTime ultimaReposicion;
    
    private Boolean alertaStockCritico; 
}