package com.Vet.Mascotas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class mascotaResponseDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Long duenioId;
}