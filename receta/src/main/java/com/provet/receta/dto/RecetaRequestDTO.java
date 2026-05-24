package com.provet.receta.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaRequestDTO {
// Solo pedimos los datos que el veterinario digita en la consulta
    private Long idMascota;
    private Long idCita;
    private String diagnostico;
    private LocalDate fechaVencimiento; // Cambiado a LocalDate para que coincida 1:1 con el modelo
    private List<DetalleRecetaRequestDTO> detalles;
}
