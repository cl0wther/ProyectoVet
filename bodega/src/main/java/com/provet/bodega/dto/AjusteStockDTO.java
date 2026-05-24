package com.provet.bodega.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjusteStockDTO {
    private Long idProducto;
    private Integer cantidad; // Cuánto hay que restar (en caso de venta) o sumar (en caso de devolución)
}