package com.provet.bodega.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_producto", nullable = false, unique = true)
    private Long idProducto; 

    // --- CONTROL DE CANTIDADES ---
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo; // El límite donde el sistema debe alertar: "¡Hay que pedir más Amoxicilina!"

    // --- LOGÍSTICA FÍSICA ---
    @Column(name = "ubicacion", length = 50)
    private String ubicacion; // Ej: "Pasillo 4, Estante B"

    @Column(name = "ultima_reposicion")
    private LocalDateTime ultimaReposicion; // Para saber cuándo fue la última vez que entró un camión con este producto
}