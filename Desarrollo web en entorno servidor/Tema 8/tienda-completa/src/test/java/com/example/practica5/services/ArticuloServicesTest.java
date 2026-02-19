package com.example.practica5.services;

import com.example.practica5.dto.ArticuloResponseDTO;
import com.example.practica5.models.Articulo;
import com.example.practica5.repositories.ArticuloRepository;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticuloServicesTest {

    @Mock
    private ArticuloRepository articuloRepository;

    @InjectMocks
    private ArticuloService articuloService;

    @Test
    public void  testFindAllArticulos(){
        //PREPARAR DATOS DE PRUEBA
        System.out.println("Preparamos los datos para la prueba:");
        Articulo SN123 = new Articulo("SN123", "Teclado",new BigDecimal("25.20"),5,null);
        Articulo SN124 = new Articulo("SN124", "Raton",new BigDecimal("18.50"),10,null);
        List<Articulo> listaTest = new ArrayList<>(Arrays.asList(SN123,SN124));



        //CONFIGURAMOS EL COMPORTAMIENTO DEL MOCK
        System.out.println("Comprobando el comportamiento del Mock");
        when(articuloRepository.findAll()).thenReturn(listaTest);

        System.out.println("Ejecutando...");
        List<ArticuloResponseDTO> respuesta = articuloService.findAll();

        System.out.println("Comprobando si no es null");
        assertNotNull(respuesta);

        System.out.println("Comprobando si es la respuesta esperada (2 en la lista)");
        assertEquals(2, respuesta.size());

        System.out.println("Comprobando datos de la prueba son iguales");
        System.out.println("Comprobando el primero..");
        assertEquals("SN123",respuesta.get(0).getNumeroSerie());
        assertEquals("Teclado",respuesta.get(0).getNombreProducto());
        assertEquals(new BigDecimal("25.20"),respuesta.get(0).getPrecioVenta());
        assertEquals(5,respuesta.get(0).getStockDisponible());

        System.out.println("Comprobando el segundo..");
        assertEquals("SN124",respuesta.get(1).getNumeroSerie());
        assertEquals("Raton",respuesta.get(1).getNombreProducto());
        assertEquals(new BigDecimal("18.50"),respuesta.get(1).getPrecioVenta());
        assertEquals(10,respuesta.get(1).getStockDisponible());

        System.out.println("Comprobando si se ha llamado las veces necesarias");
        verify(articuloRepository,atLeastOnce()).findAll();

        System.out.println("La prueba ha terminado sin errores. Todo Ok");

    }
}
