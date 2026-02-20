package com.example.practica5.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practica5.models.Articulo;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, String> {
    
    List<Articulo> findByStockDisponibleEquals(Integer stock);
    
    List<Articulo> findByPrecioVentaLessThan(BigDecimal precio);
    
    List<Articulo> findByNombreProductoContainingIgnoreCase(String nombre);
    
    List<Articulo> findAllByOrderByPrecioVentaAsc();
    
    List<Articulo> findTop5ByOrderByPrecioVentaAsc();
}
