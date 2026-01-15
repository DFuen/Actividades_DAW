package com.example.Tienda.repository;

import ch.qos.logback.core.net.server.Client;
import com.example.Tienda.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealizaPedido  extends JpaRepository<Cliente, String> {
}
