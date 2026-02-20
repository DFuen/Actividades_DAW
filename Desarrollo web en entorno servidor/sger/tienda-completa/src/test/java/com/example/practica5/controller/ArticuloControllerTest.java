package com.example.practica5.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.practica5.controllers.ArticuloController;
import com.example.practica5.dto.ArticuloResponseDTO;
import com.example.practica5.exceptions.ArticuloNotFoundException;
import com.example.practica5.models.Articulo;
import com.example.practica5.services.ArticuloService;

@ExtendWith(MockitoExtension.class)
public class ArticuloControllerTest {

    @Mock
    private ArticuloService articuloService;

    @InjectMocks
    private ArticuloController articuloController;


    @Test
    public void testFindAllArticulos() {
        System.out.println("=== Iniciando test de FindAllArticulos ===");

        List<ArticuloResponseDTO> articulos = Arrays.asList(
                new ArticuloResponseDTO("SN123", "Laptop Dell XPS", new BigDecimal("999.99"), 5),
                new ArticuloResponseDTO("SN124", "Mouse Logitech", new BigDecimal("29.99"), 10)
        );

        when(articuloService.findAll()).thenReturn(articulos);

        System.out.println("✓ Mock configurado correctamente");
        
        List<ArticuloResponseDTO> resultado = articuloController.findAll();

        System.out.println("✓ Método findAll() ejecutado\n");

        System.out.println("✓ Verificando que la respuesta no es null...");
        assertNotNull(resultado);
        
        System.out.println("✓ Verificando tamaño de la lista...");
        assertEquals(2, resultado.size());
        
        System.out.println("✓ Verificando primer artículo - número de serie...");
        assertEquals("SN123", resultado.get(0).getNumeroSerie());
        
        System.out.println("✓ Verificando primer artículo - nombre del producto...");
        assertEquals("Laptop Dell XPS", resultado.get(0).getNombreProducto());
        
        System.out.println("✓ Verificando primer artículo - precio venta...");
        assertEquals(new BigDecimal("999.99"), resultado.get(0).getPrecioVenta());
        
        System.out.println("✓ Verificando primer artículo - stock disponible...");
        assertEquals(5, resultado.get(0).getStockDisponible());
        
        System.out.println("✓ Verificando segundo artículo - número de serie...");
        assertEquals("SN124", resultado.get(1).getNumeroSerie());
        
        System.out.println("✓ Verificando segundo artículo - nombre del producto...");
        assertEquals("Mouse Logitech", resultado.get(1).getNombreProducto());
        
        System.out.println("✓ Verificando segundo artículo - precio venta...");
        assertEquals(new BigDecimal("29.99"), resultado.get(1).getPrecioVenta());
        
        System.out.println("✓ Verificando segundo artículo - stock disponible...");
        assertEquals(10, resultado.get(1).getStockDisponible());

        System.out.println("\n✓ Verificando que el servicio fue llamado una sola vez...");
        verify(articuloService, times(1)).findAll();
        
        System.out.println("\n=== Test EXITOSO ===\n");
    }

    @Test
    public void testFindArticuloByIdExitoso() {
        System.out.println("=== Iniciando test de FindArticuloByIdExitoso ===");

        ArticuloResponseDTO articuloEsperado = new ArticuloResponseDTO(
                "SN123",
                "Laptop Dell XPS",
                new BigDecimal("999.99"),
                5
        );

        when(articuloService.findById("SN123")).thenReturn(articuloEsperado);

        System.out.println("✓ Mock configurado para retornar artículo con ID SN123");
        
        ArticuloResponseDTO resultado = articuloController.findById("SN123");

        System.out.println("✓ Método findById() ejecutado\n");

        System.out.println("✓ Verificando que la respuesta no es null...");
        assertNotNull(resultado);
        
        System.out.println("✓ Verificando número de serie...");
        assertEquals("SN123", resultado.getNumeroSerie());
        
        System.out.println("✓ Verificando nombre del producto...");
        assertEquals("Laptop Dell XPS", resultado.getNombreProducto());
        
        System.out.println("✓ Verificando precio venta...");
        assertEquals(new BigDecimal("999.99"), resultado.getPrecioVenta());
        
        System.out.println("✓ Verificando stock disponible...");
        assertEquals(5, resultado.getStockDisponible());

        System.out.println("\n✓ Verificando que el servicio fue llamado con ID correcto...");
        verify(articuloService, times(1)).findById("SN123");
        
        System.out.println("\n=== Test EXITOSO ===\n");
    }

    @Test
    public void testFindArticuloByIdNotFound() {
        System.out.println("=== Iniciando test de FindArticuloByIdNotFound ===");

        String idInvalido = "INVALID";
        ArticuloNotFoundException excepcionEsperada = new ArticuloNotFoundException(
                "Articulo con numero de serie " + idInvalido + " no encontrado"
        );

        when(articuloService.findById(idInvalido))
                .thenThrow(excepcionEsperada);

        System.out.println("✓ Mock configurado para lanzar excepción con ID " + idInvalido);
        
        System.out.println("✓ Intentando buscar artículo con ID inválido...");
        ArticuloNotFoundException excepcionCapturada = assertThrows(
                ArticuloNotFoundException.class,
                () -> articuloController.findById(idInvalido)
        );

        System.out.println("✓ Excepción capturada exitosamente\n");

        System.out.println("✓ Verificando que la excepción no es null...");
        assertNotNull(excepcionCapturada);
        
        System.out.println("✓ Verificando mensaje de la excepción...");
        assertEquals("Articulo con numero de serie INVALID no encontrado", 
                excepcionCapturada.getMessage());

        System.out.println("\n✓ Verificando que el servicio fue consultado...");
        verify(articuloService, times(1)).findById(idInvalido);
        
        System.out.println("\n=== Test EXITOSO ===\n");
    }

    @Test
    public void testSaveArticulo() {
        System.out.println("=== Iniciando test de SaveArticulo ===");

        Articulo articuloNuevo = new Articulo(
                "SN125",
                "Monitor Samsung",
                new BigDecimal("299.99"),
                10,
                null
        );

        ArticuloResponseDTO articuloGuardado = new ArticuloResponseDTO(
                "SN125",
                "Monitor Samsung",
                new BigDecimal("299.99"),
                10
        );

        when(articuloService.save(articuloNuevo)).thenReturn(articuloGuardado);

        System.out.println("✓ Mock configurado para guardar artículo");
        System.out.println("✓ Artículo nuevo creado: SN125 - Monitor Samsung\n");
        
        ArticuloResponseDTO resultado = articuloController.save(articuloNuevo);

        System.out.println("✓ Método save() ejecutado");
        
        System.out.println("✓ Verificando que la respuesta no es null...");
        assertNotNull(resultado);
        
        System.out.println("✓ Verificando número de serie...");
        assertEquals("SN125", resultado.getNumeroSerie());
        
        System.out.println("✓ Verificando nombre del producto...");
        assertEquals("Monitor Samsung", resultado.getNombreProducto());
        
        System.out.println("✓ Verificando precio venta...");
        assertEquals(new BigDecimal("299.99"), resultado.getPrecioVenta());
        
        System.out.println("✓ Verificando stock disponible...");
        assertEquals(10, resultado.getStockDisponible());

        System.out.println("\n✓ Verificando que el servicio save() fue llamado una sola vez...");
        verify(articuloService, times(1)).save(articuloNuevo);
        
        System.out.println("\n=== Test EXITOSO ===\n");
    }

    @Test
    public void testDeleteArticulo() {
        System.out.println("=== Iniciando test de DeleteArticulo ===");

        String idArticulo = "SN123";
        doNothing().when(articuloService).delete(idArticulo);

        System.out.println("✓ Mock configurado para eliminar artículo con ID " + idArticulo);
        
        System.out.println("✓ Ejecutando método delete()...");
        articuloController.delete(idArticulo);

        System.out.println("✓ Método delete() ejecutado sin excepciones\n");

        System.out.println("✓ Verificando que el servicio delete() fue llamado una sola vez...");
        verify(articuloService, times(1)).delete(idArticulo);
        
        System.out.println("✓ Verificando que se llamó con el ID correcto...");
        
        System.out.println("\n=== Test EXITOSO ===\n");
    }

}
