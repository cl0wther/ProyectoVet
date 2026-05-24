package com.provet.bodega.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioRequestDTO {
    private Long idProducto;
    private Integer stockInicial; // Con cuánto empezamos
    private Integer stockMinimo;  // Cuándo debe saltar la alarma
    private String ubicacion;     // Ej: "Bodega Central - Estante 2"
}