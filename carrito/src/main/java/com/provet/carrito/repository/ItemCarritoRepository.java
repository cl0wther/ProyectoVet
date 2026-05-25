package com.provet.carrito.repository;

import com.provet.carrito.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ItemCarritoRepository
 *
 * Clínica Veterinaria Provet
 */
@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
}