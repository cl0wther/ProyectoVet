package com.provet.pedidos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodegaDTO {
    private Long id;
    private Long idProducto;
    private Integer stockActual;
    private Integer stockMinino;
    private String ubicacion;
    private LocalDateTime ultimaReposicion;

}
