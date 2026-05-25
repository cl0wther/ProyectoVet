package com.provet.duenio.dto;

// Importamos las validaciones igual que en el modelo
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DuenioRequestDTO {

    // Validamos que el nombre no venga vacío desde el frontend
    @NotBlank(message = "El nombre del dueño no puede ser vacio")
    private String nombre;

    // Validamos que el correo sea obligatorio
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    // Validamos que el teléfono sea obligatorio
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
    
}