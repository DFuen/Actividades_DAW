package com.example.Tarea1.controller;

import com.example.Tarea1.model.Libro;
import com.example.Tarea1.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class librosController {

    @Autowired private LibroRepository libroRepository;

    @GetMapping
    public List<Libro> obtenerLibros(){
        return libroRepository.findAll();
    }

    @PostMapping
    public  Libro guardarLibro(@RequestBody Libro libro){
        return libroRepository.save(libro);
    }
}
