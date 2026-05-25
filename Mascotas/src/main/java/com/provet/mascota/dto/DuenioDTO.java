package com.provet.mascota.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuenioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;

}
