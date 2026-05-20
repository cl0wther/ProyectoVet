package com.provet.catalogo.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "El nombre no puede ser vacio")
    @Column(nullable = false, length = 200)
    private String nombreMed;

    @Column(nullable = false, precision = 10)
    private BigDecimal precioMed;

    @Column(length = 500)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "categoriaId", nullable = false)
    private CategoriaMed categoria;


    

}
