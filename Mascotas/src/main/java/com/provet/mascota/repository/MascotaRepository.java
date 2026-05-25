package com.provet.mascota.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.provet.mascota.model.Mascotas;

public interface MascotaRepository extends JpaRepository<Mascotas, Long> {
    
    List<Mascotas> findByDuenioId(Long duenioId);
}