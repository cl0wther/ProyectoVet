package com.provet.favoritos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoRequestDTO {

    @NotNull(message = "El medicamentoId es obligatorio")
    private Long medicamentoId;

    @NotBlank(message = "El usuario no puede estar vacío")
    private String usuario;
}