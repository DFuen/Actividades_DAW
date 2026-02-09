package com.example.practica5.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practica5.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    List<Pedido> findByEstado(String estado);
    
    List<Pedido> findByFechaPedidoBetween(LocalDateTime inicio, LocalDateTime fin);
    
    Optional<Pedido> findFirstByOrderByFechaPedidoDesc();
    
    List<Pedido> findByEstadoNot(String estado);
    
    Long countByEstado(String estado);
}
