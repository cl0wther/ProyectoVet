package com.provet.receta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.provet.receta.model.Receta;

import org.springframework.data.repository.query.Param;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findByIdMascota(Long idMascota);
    
    Optional<Receta> findByIdCita (Long idCita); 

    @Query("SELECT r FROM Receta r JOIN r.detalles d WHERE r.idMascota = :idMascota " +
           "AND d.idMedicamento = :idMedicamento " +
           "AND r.estado = 'DISPONIBLE' " +
           "AND r.fechaVencimiento >= CURRENT_DATE")
    List<Receta> buscarRecetasValidasParaCompra(
            @Param("idMascota") Long idMascota, 
            @Param("idMedicamento") Long idMedicamento
    );


}
