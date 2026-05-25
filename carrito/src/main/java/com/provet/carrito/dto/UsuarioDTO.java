package com.provet.carrito.dto;

import lombok.Data;

/**
 * UsuarioDTO
 *
 * Clínica Veterinaria Provet
 * Espejo local de la respuesta del microservicio de Usuarios.
 * No se guarda en la base de datos del carrito, solo se usa para transferir datos.
 */
@Data
public class UsuarioDTO {
    
    private Long id;
    
    // Identificador único del usuario
    private String username; 
    
    // Datos personales del dueño de la mascota
    private String nombre;
    private String email;
    private String telefono;
    
    // Útil para calcular costos de envío si venden con despacho
    private String direccion; 
}