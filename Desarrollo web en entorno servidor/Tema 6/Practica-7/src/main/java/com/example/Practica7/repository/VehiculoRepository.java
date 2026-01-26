package com.example.Practica7.repository;

import com.example.Practica7.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository <Vehiculo, String> {

    List<Vehiculo> findBymarca(String marca);
    List<Vehiculo> findByanioBetween(Integer uno,Integer dos);
    List<Vehiculo> findBymarcaStartingWithIgnoreCaseAnddisponible(String letra,boolean disp);
    Long countByVehiculosLessThan(Integer coste);

}
