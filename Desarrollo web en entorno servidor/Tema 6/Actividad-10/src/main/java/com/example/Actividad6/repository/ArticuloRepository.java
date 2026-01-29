package com.example.Actividad6.repository;

import com.example.Actividad6.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    List<Articulo> findBystock(Integer n);
    List<Articulo> findByprecioVentaLessThan(BigDecimal p);
    List<Articulo> findByNombreContainingIgnoreCase(String l);
    List<Articulo> findBynombreOrderByprecioVentaAsc();
    List<Articulo> findTop5ByprecioVentaAsc();
}
