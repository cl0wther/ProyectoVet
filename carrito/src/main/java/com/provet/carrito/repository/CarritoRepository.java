package com.provet.carrito.repository;

import com.provet.carrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CarritoRepository
 *
 * Clínica Veterinaria Provet
 */
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    
    // Permite buscar todos los carritos asociados a un cliente específico
    List<Carrito> findByUsuario(String usuario);
}