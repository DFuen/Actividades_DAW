package com.example.practica5.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practica5.dto.ArticuloResponseDTO;
import com.example.practica5.exceptions.ArticuloNotFoundException;
import com.example.practica5.models.Articulo;
import com.example.practica5.repositories.ArticuloRepository;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    public List<ArticuloResponseDTO> findAll() {
        List<Articulo> articulos = articuloRepository.findAll();
        List<ArticuloResponseDTO> result = new ArrayList<>(articulos.size());
        for (Articulo articulo : articulos) {
            result.add(new ArticuloResponseDTO(
                    articulo.getNumeroSerie(),
                    articulo.getNombreProducto(),
                    articulo.getPrecioVenta(),
                    articulo.getStockDisponible()));
        }
        return result;
    }

    public ArticuloResponseDTO findById(String id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("Articulo con numero de serie " + id + " no encontrado"));
        return new ArticuloResponseDTO(
                articulo.getNumeroSerie(),
                articulo.getNombreProducto(),
                articulo.getPrecioVenta(),
                articulo.getStockDisponible());
    }

    public ArticuloResponseDTO save(Articulo articulo) {
        Articulo saved = articuloRepository.save(articulo);
        return new ArticuloResponseDTO(
                saved.getNumeroSerie(),
                saved.getNombreProducto(),
                saved.getPrecioVenta(),
                saved.getStockDisponible());
    }

    public ArticuloResponseDTO update(String id, Articulo articulo) {
        Articulo existente = articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("Articulo con numero de serie " + id + " no encontrado"));
        existente.setNombreProducto(articulo.getNombreProducto());
        existente.setPrecioVenta(articulo.getPrecioVenta());
        existente.setStockDisponible(articulo.getStockDisponible());
        Articulo saved = articuloRepository.save(existente);
        return new ArticuloResponseDTO(
            saved.getNumeroSerie(),
            saved.getNombreProducto(),
            saved.getPrecioVenta(),
            saved.getStockDisponible());
    }

    public void delete(String id) {
        articuloRepository.deleteById(id);
    }

    public List<ArticuloResponseDTO> findArticulosSinStock() {
        List<Articulo> articulos = articuloRepository.findByStockDisponibleEquals(0);
        List<ArticuloResponseDTO> result = new ArrayList<>(articulos.size());
        for (Articulo articulo : articulos) {
            result.add(new ArticuloResponseDTO(
                    articulo.getNumeroSerie(),
                    articulo.getNombreProducto(),
                    articulo.getPrecioVenta(),
                    articulo.getStockDisponible()));
        }
        return result;
    }

    public List<ArticuloResponseDTO> findArticulosPorPrecio(BigDecimal maxPrecio) {
        List<Articulo> articulos = articuloRepository.findByPrecioVentaLessThan(maxPrecio);
        List<ArticuloResponseDTO> result = new ArrayList<>(articulos.size());
        for (Articulo articulo : articulos) {
            result.add(new ArticuloResponseDTO(
                    articulo.getNumeroSerie(),
                    articulo.getNombreProducto(),
                    articulo.getPrecioVenta(),
                    articulo.getStockDisponible()));
        }
        return result;
    }

    public List<ArticuloResponseDTO> findArticulosPorNombre(String palabra) {
        List<Articulo> articulos = articuloRepository.findByNombreProductoContainingIgnoreCase(palabra);
        List<ArticuloResponseDTO> result = new ArrayList<>(articulos.size());
        for (Articulo articulo : articulos) {
            result.add(new ArticuloResponseDTO(
                    articulo.getNumeroSerie(),
                    articulo.getNombreProducto(),
                    articulo.getPrecioVenta(),
                    articulo.getStockDisponible()));
        }
        return result;
    }

    public List<ArticuloResponseDTO> findArticulosOrdenadosPorPrecio() {
        List<Articulo> articulos = articuloRepository.findAllByOrderByPrecioVentaAsc();
        List<ArticuloResponseDTO> result = new ArrayList<>(articulos.size());
        for (Articulo articulo : articulos) {
            result.add(new ArticuloResponseDTO(
                    articulo.getNumeroSerie(),
                    articulo.getNombreProducto(),
                    articulo.getPrecioVenta(),
                    articulo.getStockDisponible()));
        }
        return result;
    }

    public List<ArticuloResponseDTO> findTop5Baratos() {
        List<Articulo> articulos = articuloRepository.findTop5ByOrderByPrecioVentaAsc();
        List<ArticuloResponseDTO> result = new ArrayList<>(articulos.size());
        for (Articulo articulo : articulos) {
            result.add(new ArticuloResponseDTO(
                    articulo.getNumeroSerie(),
                    articulo.getNombreProducto(),
                    articulo.getPrecioVenta(),
                    articulo.getStockDisponible()));
        }
        return result;
    }
}
