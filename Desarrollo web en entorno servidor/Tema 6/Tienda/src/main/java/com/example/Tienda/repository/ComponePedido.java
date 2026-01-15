package com.example.Tienda.repository;

import com.example.Tienda.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponePedido extends JpaRepository<Pedido, Integer> {
}
