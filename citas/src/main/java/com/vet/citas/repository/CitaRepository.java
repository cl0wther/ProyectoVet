package com.vet.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vet.citas.model.Cita;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    
    List<Cita> findByMascotaId(Long mascotaId);
}