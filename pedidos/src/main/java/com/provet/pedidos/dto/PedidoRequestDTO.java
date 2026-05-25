package com.provet.pedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {

    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;

    @NotNull(message = "El medicamentoId es obligatorio")
    private Long medicamentoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima para procesar un pedido es 1")
    private Integer cantidad;

    private String metodoPago;
}