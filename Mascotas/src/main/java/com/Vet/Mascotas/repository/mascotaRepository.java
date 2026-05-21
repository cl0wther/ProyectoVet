package com.Vet.Mascotas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Vet.Mascotas.model.mascota;

public interface mascotaRepository extends JpaRepository<mascota, Long> {
    
    List<mascota> findByDuenioId(Long duenioId);
}