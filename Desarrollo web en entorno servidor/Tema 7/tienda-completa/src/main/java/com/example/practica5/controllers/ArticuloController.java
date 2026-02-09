package com.example.practica5.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.practica5.dto.ArticuloDTO;
import com.example.practica5.services.ArticuloService;
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

import com.example.practica5.models.Articulo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping
    public List<ArticuloDTO> findAll() {
        return articuloService.listarArticulos();
    }

    @GetMapping("/{id}")
    public Optional<Articulo> findById(@PathVariable String id) {
        return articuloService.buscarArticulo(id);
    }

    @PostMapping
    public Articulo save(@Valid @RequestBody Articulo articulo) {
        return articuloService.guardarArticulo(articulo);
   }

   @PutMapping("/{id}")
    public ArticuloDTO update(@PathVariable String id, @Valid @RequestBody Articulo articulo) {
        return articuloService.actualizarArticulo(id,articulo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
       articuloService.borrarArticulo(id);
    }

    @GetMapping("/buscar/sin-stock")
    public List<Articulo> findArticulosSinStock() {
        return articuloService.sinStock();
    }

    @GetMapping("/buscar/por-precio")
    public List<Articulo> findArticulosPorPrecio(@RequestParam BigDecimal maxPrecio) {
        return articuloService.buscarPrecio(maxPrecio);
    }

    @GetMapping("/buscar/por-nombre")
    public List<Articulo> findArticulosPorNombre(@RequestParam String palabra) {
        return articuloService.porNombre(palabra);
    }

    @GetMapping("/ordenados-por-precio")
    public List<Articulo> findArticulosOrdenadosPorPrecio() {
        return articuloService.porPrecio();
    }

    @GetMapping("/top-5-baratos")
    public List<Articulo> findTop5Baratos() {
        return articuloService.topCinco();
    }
}
