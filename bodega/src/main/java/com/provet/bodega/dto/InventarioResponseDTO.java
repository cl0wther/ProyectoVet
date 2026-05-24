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
    
    // Un pequeño dato extra que podemos calcular en el Service para ayudar al Frontend:
    private Boolean alertaStockCritico; // Será 'true' si stockActual <= stockMinimo
}