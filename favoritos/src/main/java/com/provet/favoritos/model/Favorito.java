package com.provet.favoritos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "favoritos")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long medicamentoId;

    @Column(nullable = false)
    private String nombreMedicamento;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private LocalDateTime fechaAgregado;
}