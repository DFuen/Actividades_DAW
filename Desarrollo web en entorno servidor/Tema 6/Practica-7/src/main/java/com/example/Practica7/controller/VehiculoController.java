package com.example.Practica7.controller;

import com.example.Practica7.model.Vehiculo;
import com.example.Practica7.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/vehiculo")
@RestController
public class VehiculoController {

    @Autowired
    VehiculoRepository vehiculoRepository;

    @GetMapping("/buscar/{marca}")
    public List<Vehiculo> buscarMarca(@PathVariable String marca){
        return vehiculoRepository.findBymarca(marca);
    }

    @GetMapping("/buscaranio/{aniouno}{aniodos}")
    public List<Vehiculo> buscarAnio(@PathVariable Integer aniouno,@PathVariable Integer aniodos){
        return vehiculoRepository.findByanioBetween(aniouno,aniodos);
    }

    @GetMapping("/buscarmarcaL/{letra}")
    public List<Vehiculo> buscarMarcaLetra(@PathVariable String l){
        return vehiculoRepository.findBymarcaStartingWithIgnoreCaseAnddisponible(l,true);
    }

    @GetMapping("/cantidad/{precio}")
    public Long contarVehiculosPrecio(@PathVariable Integer p){
        return vehiculoRepository.countByVehiculosLessThan(p);
    }


}
