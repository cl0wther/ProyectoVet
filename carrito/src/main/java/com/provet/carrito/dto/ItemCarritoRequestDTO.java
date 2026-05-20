package com.provet.carrito.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemCarritoRequestDTO {
    
    @NotNull(message = "El medicamentoId es obligatorio")
    private Long medicamentoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima a agregar es 1")
    private Integer cantidad;
}
