package com.provet.pagos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- REFERENCIA AL MICROSERVICIO DE PEDIDOS ---
    // Guardamos solo el ID del pedido para mantener los microservicios desacoplados
    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    // --- DATOS DE LA TRANSACCIÓN ---
    @Column(name = "monto_total", nullable = false)
    private BigDecimal montoTotal;

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago; // Ej: "TARJETA_CREDITO", "TARJETA_DEBITO", "TRANSFERENCIA"

    @Column(name = "estado", nullable = false)
    private String estado; // Ej: "PENDIENTE", "COMPLETADO", "RECHAZADO"

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    // Opcional: Un código simulado que te devolvería una pasarela de pagos (como Transbank, PayPal, etc.)
    @Column(name = "codigo_transaccion")
    private String codigoTransaccion; 
}