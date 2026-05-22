package com.vet.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaResponseDTO {
    private Long id;
    private Long mascotaId;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
}