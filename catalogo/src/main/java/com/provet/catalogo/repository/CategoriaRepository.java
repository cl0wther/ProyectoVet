package com.provet.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provet.catalogo.model.CategoriaMed;

public interface CategoriaRepository extends JpaRepository<CategoriaMed, Long>{

}
