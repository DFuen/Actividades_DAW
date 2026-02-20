package com.example.practica5.controllers;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Articulos",description = "Gestión de articulos")
@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @Operation(summary = "Listar todos los articulos", description = "Se obtiene una lista de articulos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Articulos encontrados correctamente")
    })
    @GetMapping
    public List<ArticuloResponseDTO> findAll() {
        return articuloService.findAll();
    }

    @Operation(summary = "Buscar articulo por id", description = "Se obtiene un articulo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Articulo encontrado correctamente"),
            @ApiResponse(responseCode = "404",description = "Articulo no encontrado")
    })
    @GetMapping("/{id}")
    public ArticuloResponseDTO findById(@PathVariable String id) {
        return articuloService.findById(id);
    }

    @Operation(summary = "Crea un articulo", description = "Se crea un articulo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Articulo creado correctamente"),
    })
    @PostMapping
    public ArticuloResponseDTO save(@Valid @RequestBody Articulo articulo) {
        return articuloService.save(articulo);
    }

    @Operation(summary = "Actualiza un articulo", description = "Actualiza un articulo de la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Articulo ha sido actualizado correctamente"),
    })
    @PutMapping("/{id}")
    public ArticuloResponseDTO update(@PathVariable String id, @Valid @RequestBody Articulo articulo) {
        return articuloService.update(id, articulo);
    }

    @Operation(summary = "Elimina un articulo", description = "Elimina un articulo de la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "203",description = "Articulo ha sido eliminado correctamente y no debuelve respuesta"),
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        articuloService.delete(id);
    }

    @Operation(summary = "Busca articulos sin Stock", description = "Busca articulos en la lista sin Stock de la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Han encontrado los articulos sin stock correctamente"),
    })
    @GetMapping("/buscar/sin-stock")
    public List<ArticuloResponseDTO> findArticulosSinStock() {
        return articuloService.findArticulosSinStock();
    }

    @Operation(summary = "Busca articulos por precio", description = "Busca articulos por precio indicado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Han encontrado los articulos con el precio indicado correctamente"),
    })
    @GetMapping("/buscar/por-precio")
    public List<ArticuloResponseDTO> findArticulosPorPrecio(@RequestParam BigDecimal maxPrecio) {
        return articuloService.findArticulosPorPrecio(maxPrecio);
    }

    @Operation(summary = "Busca articulos por nombre", description = "Busca articulos por nombre indicado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Han encontrado los articulos con el nombre indicado correctamente"),
    })
    @GetMapping("/buscar/por-nombre")
    public List<ArticuloResponseDTO> findArticulosPorNombre(@RequestParam String palabra) {
        return articuloService.findArticulosPorNombre(palabra);
    }

    @Operation(summary = "Ordena los articulos por precio", description = "Ordena articulos por precio en una lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Se han ordenado correctamente en una lista"),
    })
    @GetMapping("/ordenados-por-precio")
    public List<ArticuloResponseDTO> findArticulosOrdenadosPorPrecio() {
        return articuloService.findArticulosOrdenadosPorPrecio();
    }

    @Operation(summary = "Lista los 5 articulos mas baratos", description = "Lista los 5 articulos mas baratos de la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Se han ordenado correctamente en una lista"),
    })
    @GetMapping("/top-5-baratos")
    public List<ArticuloResponseDTO> findTop5Baratos() {
        return articuloService.findTop5Baratos();
    }
}
