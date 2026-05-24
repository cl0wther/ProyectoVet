package com.provet.receta.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalle_receta")
public class DetalleReceta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

// --- REFERENCIA AL MICROSERVICIO DE CATÁLOGO ---
    @Column(name = "id_medicamento", nullable = false)
    private Long idMedicamento; 

    // --- INDICACIONES MÉDICAS ---
    private String dosis; // Ej: "1 comprimido cada 12 horas por 7 días"
    
    // --- RELACIÓN CON LA RECETA ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receta_id", nullable = false)
    //@JsonIgnore // Evita un bucle infinito al responder la petición HTTP
    private Receta receta;


}
