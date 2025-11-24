package com.example.Actividad4.controller;

import com.example.Actividad4.dto.PeticionBusquedaDTO;
import com.example.Actividad4.dto.RespuestaProductoDTO;
import com.example.Actividad4.model.Producto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class tiendaController {

    private ObjectMapper objectMapper;
    private List<Producto> productos;

    public tiendaController(ObjectMapper objectMapper){
        try{
            ClassPathResource resource = new ClassPathResource("productos.json");
            InputStream inputStream = resource.getInputStream();
            productos = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Producto>>(){}
            );
        }
        catch (IOException e){
            productos = new ArrayList<>();
        }
    }

    @PostMapping("/buscarPedido")
    public RespuestaProductoDTO buscarPedido(@RequestBody PeticionBusquedaDTO peticionBusquedaDTO) {

        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(peticionBusquedaDTO.getNombre())) {
                return new RespuestaProductoDTO(p.getNombre(), p.getPrecio());
            }
        }
        return new RespuestaProductoDTO(peticionBusquedaDTO.getNombre(),0.0);
    }

    @PostMapping("/buscarPedidos")
    public List<RespuestaProductoDTO> buscarPedidos(@RequestBody PeticionBusquedaDTO peticionBusquedaDTO) {

        List<Producto> listaProductos = new ArrayList<>();

        for (Producto p : productos){
            if(peticionBusquedaDTO.getNombre().toLowerCase().equals(p.getNombre().toLowerCase())){
                listaProductos.add(new RespuestaProductoDTO(p.getNombre(),p.getPrecio()));
            }
        }
    }

}
