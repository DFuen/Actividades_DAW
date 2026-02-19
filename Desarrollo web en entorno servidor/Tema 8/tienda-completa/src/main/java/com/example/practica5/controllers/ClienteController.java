package com.example.practica5.controllers;

import java.util.List;

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

import com.example.practica5.dto.ClienteResponseDTO;
import com.example.practica5.models.Cliente;
import com.example.practica5.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteResponseDTO> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{dni}")
    public ClienteResponseDTO findById(@PathVariable String dni) {
        return clienteService.findById(dni);
    }

    @PostMapping
    public ClienteResponseDTO save(@Valid @RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("/{dni}")
    public ClienteResponseDTO update(@PathVariable String dni, @Valid @RequestBody Cliente cliente) {
        return clienteService.update(dni, cliente);
    }

    @DeleteMapping("/{dni}")
    public void delete(@PathVariable String dni) {
        clienteService.delete(dni);
    }

    @GetMapping("/buscar/por-correo")
    public ClienteResponseDTO findByCorreo(@RequestParam String correo) {
        return clienteService.findByCorreo(correo);
    }

    @GetMapping("/buscar/por-apellido")
    public List<ClienteResponseDTO> findByApellidos(@RequestParam String apellido) {
        return clienteService.findByApellidos(apellido);
    }

    @GetMapping("/existe/{dni}")
    public Boolean existeCliente(@PathVariable String dni) {
        return clienteService.existeCliente(dni);
    }

    /**
     * Buscar clientes cuyo nombre empiece por una letra o frase
     * GET: /api/clientes/buscar/por-nombre-inicial?prefijo=Juan
     */
    @GetMapping("/buscar/por-nombre-inicial")
    public List<ClienteResponseDTO> findByNombreInicial(@RequestParam String prefijo) {
        return clienteService.findByNombreInicial(prefijo);
    }

    /**
     * Contar cuántos clientes tienen un nombre específico
     * GET: /api/clientes/contar-por-nombre?nombre=Juan
     */
    @GetMapping("/contar-por-nombre")
    public Long contarPorNombre(@RequestParam String nombre) {
        return clienteService.contarPorNombre(nombre);
    }
}
