package com.example.Actividad6.controller;

import com.example.Actividad6.model.Articulo;
import com.example.Actividad6.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/articulo")
public class ArticuloController {

    @Autowired
    ArticuloRepository articuloRepository;

    @GetMapping
    public List<Articulo> listar(){
        return articuloRepository.findAll();
    }

    /*EJERCICIO 8*/
    @GetMapping("/stock/{n}")
    public List<Articulo> listarStock(@PathVariable Integer n){
        return articuloRepository.findBystock(n);
    }

    @GetMapping("/preciomenor/{p}")
    public List<Articulo> listarPrecioMenor(@PathVariable BigDecimal p){
        return articuloRepository.findByprecioVentaLessThan(p);
    }

    @GetMapping("/nombre/{nomb}")
    public List<Articulo> listarNombre(@PathVariable String nomb){
        return articuloRepository.findByNombreContainingIgnoreCase(nomb);
    }

    @GetMapping("/ordenar")
    public List<Articulo> ordenarArticulos(){
        return articuloRepository.findBynombreOrderByprecioVentaAsc();
    }

    @GetMapping("/top5")
    public List<Articulo> topCincoArticulos(){
        return articuloRepository.findTop5ByprecioVentaAsc();
    }

    /**/

    @PostMapping
    public Articulo crearArticulo(@RequestBody Articulo nuevo){
        return articuloRepository.save(nuevo);
    }

    @PutMapping
    public Articulo actualizarArticulo(@RequestBody Articulo articulo,@PathVariable Long numero ){
        Articulo nuevo = articuloRepository.findById(numero).orElse(null);
        nuevo.setNumeroSerie(articulo.getNumeroSerie());
        nuevo.setNombreProducto(articulo.getNombreProducto());
        nuevo.setPrecioVenta(articulo.getPrecioVenta());
        nuevo.setStock(articulo.getStock());
        return articuloRepository.save(nuevo);
    }

    @DeleteMapping
    public void eliminarArticulo(@RequestBody Long numero){
        articuloRepository.deleteById(numero);
    }
}
