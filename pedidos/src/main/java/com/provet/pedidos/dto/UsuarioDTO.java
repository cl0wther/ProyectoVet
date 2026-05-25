package com.provet.pedidos.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private String nombre;
    private String email;
}