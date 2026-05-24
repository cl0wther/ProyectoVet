package com.provet.receta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.provet.receta.model.DetalleReceta;

@Repository
public interface DetalleRepository extends JpaRepository<DetalleReceta, Long>{
    // Sirve para traer todos los detalles asociados a una receta específica
    List<DetalleReceta> findByRecetaId(Long recetaId);

    // Ideal para estadísticas futuras: "Todos los detalles donde se recetó el medicamento X"
    List<DetalleReceta> findByIdMedicamento(Long idMedicamento);

}
