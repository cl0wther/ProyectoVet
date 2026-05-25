package com.provet.mascota.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MascotaRequestDTO {
    @NotBlank(message = "El nombre de la mascota no puede ser vacio")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    private String raza;

    @NotNull(message = "El ID del dueño es obligatorio")
    private Long duenioId; // Recibimos a qué dueño pertenece
}