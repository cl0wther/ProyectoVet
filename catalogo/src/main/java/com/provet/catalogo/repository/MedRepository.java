package com.provet.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provet.catalogo.model.Medicamento;
public interface MedRepository extends JpaRepository<Medicamento, Long>{
    List <Medicamento> findByCategoriaId(Long categoriaId);

}
