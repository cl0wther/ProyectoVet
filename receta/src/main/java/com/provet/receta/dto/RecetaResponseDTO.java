package com.provet.receta.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaResponseDTO {
    // Devolvemos absolutamente todo para que el cliente lo vea
    private Long id;
    private Long idMascota;
    private Long idCita;
    private String diagnostico;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String estado;
    private List<DetalleRecetaResponseDTO> detalles;
}