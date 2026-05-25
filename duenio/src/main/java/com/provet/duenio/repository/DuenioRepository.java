package com.provet.duenio.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.provet.duenio.model.Duenio;

public interface DuenioRepository extends JpaRepository<Duenio, Long> {
}