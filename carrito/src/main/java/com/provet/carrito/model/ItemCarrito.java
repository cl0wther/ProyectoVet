package com.provet.carrito.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
@ToString(exclude = "carrito")
@EqualsAndHashCode(exclude = "carrito")
@Entity
@Table(name = "items_carrito")
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long medicamentoId;

    @Column(nullable = false)
    private String nombreMedicamento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    public ItemCarrito(Long medicamentoId, String nombreMedicamento, 
                       BigDecimal precioUnitario, Integer cantidad, Carrito carrito) {
        this.medicamentoId = medicamentoId;
        this.nombreMedicamento = nombreMedicamento;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.carrito = carrito;
    }
}
