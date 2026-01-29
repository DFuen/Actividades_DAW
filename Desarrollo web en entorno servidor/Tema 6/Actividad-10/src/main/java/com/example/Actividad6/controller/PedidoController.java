package com.example.Actividad6.controller;

import com.example.Actividad6.model.Pedido;
import com.example.Actividad6.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidoRepository pedidoRepository;

    @GetMapping
    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }

    @PostMapping
    public Pedido crearArticulo(@RequestBody Pedido nuevo){
        return pedidoRepository.save(nuevo);
    }

    @PutMapping
    public Pedido actualizarArticulo(@RequestBody Pedido pedido,@PathVariable Long numero ){
        Pedido nuevo = pedidoRepository.findById(numero).orElse(null);
        nuevo.setEstado(pedido.getEstado());
        nuevo.setFechaPedido(pedido.getFechaPedido());
        return pedidoRepository.save(nuevo);
    }

    @DeleteMapping
    public void eliminarArticulo(@RequestBody Long numero){
        pedidoRepository.deleteById(numero);
    }
}
