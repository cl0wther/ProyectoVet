package com.provet.bodega.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.provet.bodega.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Optional<Inventario> findByIdProducto(Long idProducto);

    List<Inventario> findByStockActualLessThanEqual(Integer stockMinimo);

    @Query("SELECT i FROM Inventario i WHERE i.stockActual <= i.stockMinimo")
    List<Inventario> buscarProductosEnStockCritico();

    List<Inventario> findByUbicacionContainingIgnoreCase(String ubicacion);

}
