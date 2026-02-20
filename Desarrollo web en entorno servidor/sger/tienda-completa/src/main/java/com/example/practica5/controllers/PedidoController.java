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

import com.example.practica5.dto.PedidoResponseDTO;
import com.example.practica5.models.Pedido;
import com.example.practica5.services.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoResponseDTO> findAll() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public PedidoResponseDTO findById(@PathVariable Long id) {
        return pedidoService.findById(id);
    }

    @PostMapping
    public PedidoResponseDTO save(@Valid @RequestBody Pedido pedido) {
        return pedidoService.save(pedido);
    }

    @PutMapping("/{id}")
    public PedidoResponseDTO update(@PathVariable Long id, @Valid @RequestBody Pedido pedido) {
        return pedidoService.update(id, pedido);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        pedidoService.delete(id);
    }

    @GetMapping("/buscar/por-estado")
    public List<PedidoResponseDTO> findByEstado(@RequestParam String estado) {
        return pedidoService.findByEstado(estado);
    }

    @GetMapping("/buscar/por-fecha")
    public List<PedidoResponseDTO> findByFecha(@RequestParam String fecha) {
        return pedidoService.findByFecha(fecha);
    }

    @GetMapping("/ultimo")
    public PedidoResponseDTO findUltimoPedido() {
        return pedidoService.findUltimoPedido();
    }

    /**
     * Buscar pedidos que no tengan el estado "Cancelado"
     * GET: /api/pedidos/buscar/no-cancelados
     */
    @GetMapping("/buscar/no-cancelados")
    public List<PedidoResponseDTO> findNoCancelados() {
        return pedidoService.findNoCancelados();
    }

    /**
     * Buscar pedidos con estado diferente al especificado
     * GET: /api/pedidos/buscar/excepto?estado=CANCELADO
     */
    @GetMapping("/buscar/excepto")
    public List<PedidoResponseDTO> findExcepto(@RequestParam String estado) {
        return pedidoService.findExcepto(estado);
    }

    /**
     * Contar cuántos pedidos hay en un estado determinado
     * GET: /api/pedidos/contar-por-estado?estado=PENDIENTE
     */
    @GetMapping("/contar-por-estado")
    public Long contarPorEstado(@RequestParam String estado) {
        return pedidoService.contarPorEstado(estado);
    }
}
