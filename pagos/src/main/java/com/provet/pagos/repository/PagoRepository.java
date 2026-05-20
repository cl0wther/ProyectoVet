package com.provet.pagos.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.provet.pagos.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{
    Optional<Pago> findByIdPedido(Long idPedido);

    List<Pago> findByEstado(String estado);


    List<Pago> findByFechaPagoBetween(LocalDateTime inicio, LocalDateTime fin);

}
