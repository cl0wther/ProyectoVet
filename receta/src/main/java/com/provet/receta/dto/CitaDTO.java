package com.provet.receta.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaDTO {
    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private Long idMascota;
}