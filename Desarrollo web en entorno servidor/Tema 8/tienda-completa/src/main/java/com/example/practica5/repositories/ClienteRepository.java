package com.example.practica5.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practica5.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    
    Optional<Cliente> findByCorreo(String correo);
    
    List<Cliente> findByApellidos(String apellidos);
    
    List<Cliente> findByNombreStartingWithIgnoreCase(String prefijo);
    
    Long countByNombreIgnoreCase(String nombre);
}
