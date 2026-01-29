package com.example.Actividad6.controller;

import com.example.Actividad6.model.Cliente;
import com.example.Actividad6.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }


    /*EJERCICIO 8*/
    @GetMapping("/correo/{c}")
    public Optional<Cliente> listarClienteEmail(@PathVariable String c){
        return clienteRepository.findBycorreo(c);
    }

    @GetMapping("/apellido/{a}")
    public List<Cliente> listar(@PathVariable String a){
        return clienteRepository.findByapellidosContaining(a);
    }

    @GetMapping("/dni/{d}")
    public boolean existeDni(@PathVariable String d){
        return clienteRepository.exitsBydni(d);
    }

    @GetMapping("/nombre/{n}")
    public List<Cliente> listarnombre(@PathVariable String n){
        return clienteRepository.findByNombreStartingWithIgnoreCase(n);
    }

    @GetMapping("/contar/{nomb}")
    public Long contarClientes(@PathVariable String nomb){
        return clienteRepository.countBynombreIgnoreCase(nomb);
    }

    /**/

    @GetMapping("/{id}")
    public Optional<Cliente> listarCliente(@PathVariable String dni){
        return clienteRepository.findById(dni);
    }


    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente nuevo){
        return clienteRepository.save(nuevo);
    }

    @PutMapping
    public Cliente actualizarCliente(@RequestBody Cliente cliente,@PathVariable String dni ){
        Cliente nuevo = clienteRepository.findById(dni).orElse(null);
        nuevo.setDni(cliente.getDni());
        nuevo.setNombre(cliente.getNombre());
        nuevo.setApellidos(cliente.getApellidos());
        nuevo.setCorreo(cliente.getCorreo());
        nuevo.setDireccion(cliente.getDireccion());
        return clienteRepository.save(nuevo);
    }

    @DeleteMapping
    public void eliminarcliente(@RequestBody String dni){
        clienteRepository.deleteById(dni);
    }
}
