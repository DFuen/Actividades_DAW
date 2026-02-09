package com.example.practica5.controllers;

import java.util.List;
import java.util.Optional;

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

import com.example.practica5.models.Cliente;
import com.example.practica5.repositories.ClienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{dni}")
    public Optional<Cliente> findById(@PathVariable String dni) {
        return clienteRepository.findById(dni);
    }

    @PostMapping
    public Cliente save(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{dni}")
    public Cliente update(@PathVariable String dni, @Valid @RequestBody Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellidos(cliente.getApellidos());
        clienteExistente.setCorreo(cliente.getCorreo());
        clienteExistente.setDireccion(cliente.getDireccion());
        return clienteRepository.save(clienteExistente);
    }

    @DeleteMapping("/{dni}")
    public void delete(@PathVariable String dni) {
        clienteRepository.deleteById(dni);
    }

    @GetMapping("/buscar/por-correo")
    public Optional<Cliente> findByCorreo(@RequestParam String correo) {
        return clienteRepository.findByCorreo(correo);
    }

    @GetMapping("/buscar/por-apellido")
    public List<Cliente> findByApellidos(@RequestParam String apellido) {
        return clienteRepository.findByApellidos(apellido);
    }

    @GetMapping("/existe/{dni}")
    public Boolean existeCliente(@PathVariable String dni) {
        return clienteRepository.existsById(dni);
    }

    /**
     * Buscar clientes cuyo nombre empiece por una letra o frase
     * GET: /api/clientes/buscar/por-nombre-inicial?prefijo=Juan
     */
    @GetMapping("/buscar/por-nombre-inicial")
    public List<Cliente> findByNombreInicial(@RequestParam String prefijo) {
        return clienteRepository.findByNombreStartingWithIgnoreCase(prefijo);
    }

    /**
     * Contar cuántos clientes tienen un nombre específico
     * GET: /api/clientes/contar-por-nombre?nombre=Juan
     */
    @GetMapping("/contar-por-nombre")
    public Long contarPorNombre(@RequestParam String nombre) {
        return clienteRepository.countByNombreIgnoreCase(nombre);
    }
}
