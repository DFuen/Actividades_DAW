package com.example.practica5.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.example.practica5.models.Pedido;
import com.example.practica5.repositories.PedidoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Pedido> findById(@PathVariable Long id) {
        return pedidoRepository.findById(id);
    }

    @PostMapping
    public Pedido save(@Valid @RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @PutMapping("/{id}")
    public Pedido update(@PathVariable Long id, @Valid @RequestBody Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedidoExistente.setEstado(pedido.getEstado());
        return pedidoRepository.save(pedidoExistente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        pedidoRepository.deleteById(id);
    }

    @GetMapping("/buscar/por-estado")
    public List<Pedido> findByEstado(@RequestParam String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    @GetMapping("/buscar/por-fecha")
    public List<Pedido> findByFecha(@RequestParam String fecha) {
        LocalDate date = LocalDate.parse(fecha);
        LocalDateTime inicio = date.atStartOfDay();
        LocalDateTime fin = date.atTime(LocalTime.MAX);
        return pedidoRepository.findByFechaPedidoBetween(inicio, fin);
    }

    @GetMapping("/ultimo")
    public Optional<Pedido> findUltimoPedido() {
        return pedidoRepository.findFirstByOrderByFechaPedidoDesc();
    }

    /**
     * Buscar pedidos que no tengan el estado "Cancelado"
     * GET: /api/pedidos/buscar/no-cancelados
     */
    @GetMapping("/buscar/no-cancelados")
    public List<Pedido> findNoCancelados() {
        return pedidoRepository.findByEstadoNot("CANCELADO");
    }

    /**
     * Buscar pedidos con estado diferente al especificado
     * GET: /api/pedidos/buscar/excepto?estado=CANCELADO
     */
    @GetMapping("/buscar/excepto")
    public List<Pedido> findExcepto(@RequestParam String estado) {
        return pedidoRepository.findByEstadoNot(estado);
    }

    /**
     * Contar cuántos pedidos hay en un estado determinado
     * GET: /api/pedidos/contar-por-estado?estado=PENDIENTE
     */
    @GetMapping("/contar-por-estado")
    public Long contarPorEstado(@RequestParam String estado) {
        return pedidoRepository.countByEstado(estado);
    }
}
