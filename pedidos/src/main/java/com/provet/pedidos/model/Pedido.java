package com.provet.pedidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private Long medicamentoId;

    @Column(nullable = false)
    private String nombreMedicamento;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 10)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 10)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime fechaPedido;
}