package com.provet.receta.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table (name = "Receta")

public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_mascota", nullable = false)
    private Long idMascota; 
    
    @Column(name = "id_cita", nullable = false)
    private Long idCita; // Para saber de qué atención médica salió esta receta

    // --- DATOS PROPIOS DE LA RECETA ---
    
    private String diagnostico; // Ej: "Infección respiratoria leve"
    
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento; // ¡Clave para que Pedidos valide si aún sirve!
    
    @Column(name = "estado")
    private String estado; // Ej: "DISPONIBLE", "UTILIZADA", "VENCIDA"

    // --- RELACIÓN INTERNA (Dentro de la misma base de datos) ---
    
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleReceta> detalles;
} 
