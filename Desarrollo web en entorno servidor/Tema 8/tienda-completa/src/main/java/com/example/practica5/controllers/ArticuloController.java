package com.example.practica5.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.practica5.dto.ArticuloResponseDTO;
import com.example.practica5.models.Articulo;
import com.example.practica5.services.ArticuloService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping
    public List<ArticuloResponseDTO> findAll() {
        return articuloService.findAll();
    }

    @GetMapping("/{id}")
    public ArticuloResponseDTO findById(@PathVariable String id) {
        return articuloService.findById(id);
    }

    @PostMapping
    public ArticuloResponseDTO save(@Valid @RequestBody Articulo articulo) {
        return articuloService.save(articulo);
    }

    @PutMapping("/{id}")
    public ArticuloResponseDTO update(@PathVariable String id, @Valid @RequestBody Articulo articulo) {
        return articuloService.update(id, articulo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        articuloService.delete(id);
    }

    @GetMapping("/buscar/sin-stock")
    public List<ArticuloResponseDTO> findArticulosSinStock() {
        return articuloService.findArticulosSinStock();
    }

    @GetMapping("/buscar/por-precio")
    public List<ArticuloResponseDTO> findArticulosPorPrecio(@RequestParam BigDecimal maxPrecio) {
        return articuloService.findArticulosPorPrecio(maxPrecio);
    }

    @GetMapping("/buscar/por-nombre")
    public List<ArticuloResponseDTO> findArticulosPorNombre(@RequestParam String palabra) {
        return articuloService.findArticulosPorNombre(palabra);
    }

    @GetMapping("/ordenados-por-precio")
    public List<ArticuloResponseDTO> findArticulosOrdenadosPorPrecio() {
        return articuloService.findArticulosOrdenadosPorPrecio();
    }

    @GetMapping("/top-5-baratos")
    public List<ArticuloResponseDTO> findTop5Baratos() {
        return articuloService.findTop5Baratos();
    }
}
