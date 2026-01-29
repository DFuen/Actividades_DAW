package com.example.Actividad6.repository;

import com.example.Actividad6.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByestado(String est);
    List<Pedido> findByfecha(LocalDate fech);
    Optional<Pedido> findTop1ByidOrderByidDec();
}
