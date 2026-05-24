package com.provet.favoritos.repository;

import com.provet.favoritos.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    List<Favorito> findByUsuario(String usuario);

    List<Favorito> findByMedicamentoId(Long medicamentoId);

    boolean existsByMedicamentoIdAndUsuario(Long medicamentoId, String usuario);
}