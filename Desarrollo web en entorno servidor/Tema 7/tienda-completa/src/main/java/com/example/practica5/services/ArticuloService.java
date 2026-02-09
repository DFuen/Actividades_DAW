package com.example.practica5.services;

import com.example.practica5.dto.ArticuloDTO;
import com.example.practica5.models.Articulo;
import com.example.practica5.repositories.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {
    @Autowired
    private ArticuloRepository articuloRepository;
    private List<ArticuloDTO> listaArticulosCliente = new ArrayList<>();

    public List<ArticuloDTO> listarArticulos(){
        for(Articulo a : articuloRepository.findAll()){
            ArticuloDTO articulo = new ArticuloDTO();
            articulo.setNombreProducto(a.getNombreProducto());
            articulo.setPrecioVenta(a.getPrecioVenta());
            articulo.setStockDisponible(a.getStockDisponible());
            listaArticulosCliente.add(articulo);
        }
        return listaArticulosCliente;
    }

    public Optional<Articulo> buscarArticulo(String id){
        return articuloRepository.findById(id);
    }

    public Articulo guardarArticulo(Articulo articulo){
        return articuloRepository.save(articulo);
    }

    public ArticuloDTO actualizarArticulo(String id, Articulo articulo){
        articulo.setNumeroSerie(id);
        articuloRepository.save(articulo);
        ArticuloDTO a = new ArticuloDTO();
        a.setNombreProducto(articulo.getNombreProducto());
        a.setPrecioVenta(articulo.getPrecioVenta());
        a.setStockDisponible(articulo.getStockDisponible());
        return a;
    }

    public void borrarArticulo(String id){
        articuloRepository.deleteById(id);
    }

    public List<Articulo> sinStock(){
        return articuloRepository.findByStockDisponibleEquals(0);
    }

    public List<Articulo> buscarPrecio(BigDecimal maxPrecio){
        return articuloRepository.findByPrecioVentaLessThan(maxPrecio);
    }

    public List<Articulo> porNombre(String palabra){
        return articuloRepository.findByNombreProductoContainingIgnoreCase(palabra);
    }

    public List<Articulo> porPrecio(){
        return articuloRepository.findAllByOrderByPrecioVentaAsc();
    }

    public List<Articulo> topCinco(){
        return articuloRepository.findTop5ByOrderByPrecioVentaAsc();
    }

}
