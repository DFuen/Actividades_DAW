package com.example.Actividad4.controller;

import com.example.Actividad4.dto.RespuestaProductoDTO;
import com.example.Actividad4.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@RestController
public class tiendaController {

    @Autowired
    private ObjectMapper objectMapper;

//    @GetMapping("/cargar")
//    public List<Producto> leerJson(){
//        try{
//            ClassPathResource resource = new ClassPathResource("productos.json");
//            InputStream inputStream = resource.getInputStream();
//            List<Producto> productos = objectMapper.readValue(
//                    inputStream,
//                    new TypeReference<List<Producto>>(){}
//            );
//            return productos;
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    @PostMapping("/buscarPedido")
    public String buscarPedido(@RequestBody String nombre) {
        try{
            ClassPathResource resource = new ClassPathResource("productos.json");
            InputStream inputStream = resource.getInputStream();
            List<Producto> productos = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Producto>>(){}
            );
            for (Producto p: productos){
                if(p.getNombre().equalsIgnoreCase(nombre)){
                    RespuestaProductoDTO respuesta= new RespuestaProductoDTO(p.getNombre(),p.getPrecio());
                    return respuesta;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
