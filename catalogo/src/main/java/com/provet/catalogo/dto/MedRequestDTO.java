package com.provet.catalogo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MedRequestDTO {


    @NotBlank (message = "El nombre del medicamento no puede ser vacio")
    private String nombreMed;

    @NotNull (message = "El precio es obligatorio")
    @Positive (message = "El precio no puede ser negativo")
    private BigDecimal precio;

    private String descripcion;
    
    @NotNull (message = "La id de la categoria es obligatoria")
    private Long categoriaId;
}
