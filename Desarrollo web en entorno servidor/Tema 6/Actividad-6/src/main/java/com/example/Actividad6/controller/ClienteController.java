package com.example.Actividad6.controller;

import com.example.Actividad6.model.Cliente;
import com.example.Actividad6.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente nuevo){
        return clienteRepository.save(nuevo);
    }

    @PutMapping
    public Cliente actualizarCliente(@RequestBody Cliente cliente, String dni ){
        Cliente nuevo = clienteRepository.findById(dni).orElse(null);
        nuevo.setDni();
    }

    @DeleteMapping
}
