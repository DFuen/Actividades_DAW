package com.example.Actividad6.repository;

import com.example.Actividad6.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> findBycorreo(String c);
    List<Cliente> findByapellidosContaining(String a);
    boolean exitsBydni (String dni);
    List<Cliente> findByNombreStartingWithIgnoreCase(String l);
    Long countBynombreIgnoreCase(String nombre);
}
