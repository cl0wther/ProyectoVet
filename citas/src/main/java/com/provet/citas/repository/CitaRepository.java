package com.provet.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provet.citas.model.Cita;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    
    List<Cita> findByMascotaId(Long mascotaId);
}