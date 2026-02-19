package com.example.practica5.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practica5.dto.PedidoResponseDTO;
import com.example.practica5.exceptions.PedidoNotFoundException;
import com.example.practica5.models.Articulo;
import com.example.practica5.models.Pedido;
import com.example.practica5.repositories.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<PedidoResponseDTO> findAll() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResponseDTO> result = new ArrayList<>(pedidos.size());
        for (Pedido pedido : pedidos) {
            List<Articulo> articulos = pedido.getArticulos();
            List<String> articulosNumeroSerie = new ArrayList<>(articulos.size());
            for (Articulo articulo : articulos) {
                articulosNumeroSerie.add(articulo.getNumeroSerie());
            }
            result.add(new PedidoResponseDTO(
                    pedido.getId(),
                    pedido.getFechaPedido(),
                    pedido.getEstado(),
                    pedido.getCliente().getDni(),
                    articulosNumeroSerie));
        }
        return result;
    }

    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido con id " + id + " no encontrado"));
        List<Articulo> articulos = pedido.getArticulos();
        List<String> articulosNumeroSerie = new ArrayList<>(articulos.size());
        for (Articulo articulo : articulos) {
            articulosNumeroSerie.add(articulo.getNumeroSerie());
        }
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getEstado(),
                pedido.getCliente().getDni(),
                articulosNumeroSerie);
    }

    public PedidoResponseDTO save(Pedido pedido) {
        Pedido saved = pedidoRepository.save(pedido);
        List<Articulo> savedArticulos = saved.getArticulos();
        List<String> articulosNumeroSerie = new ArrayList<>(savedArticulos.size());
        for (Articulo articulo : savedArticulos) {
            articulosNumeroSerie.add(articulo.getNumeroSerie());
        }
        return new PedidoResponseDTO(
                saved.getId(),
                saved.getFechaPedido(),
                saved.getEstado(),
                saved.getCliente().getDni(),
                articulosNumeroSerie);
    }

    public PedidoResponseDTO update(Long id, Pedido pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido con id " + id + " no encontrado"));
        pedidoExistente.setEstado(pedido.getEstado());
        Pedido saved = pedidoRepository.save(pedidoExistente);
        List<Articulo> savedArticulos = saved.getArticulos();
        List<String> articulosNumeroSerie = new ArrayList<>(savedArticulos.size());
        for (Articulo articulo : savedArticulos) {
            articulosNumeroSerie.add(articulo.getNumeroSerie());
        }
        return new PedidoResponseDTO(
            saved.getId(),
            saved.getFechaPedido(),
            saved.getEstado(),
            saved.getCliente().getDni(),
            articulosNumeroSerie);
    }

    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<PedidoResponseDTO> findByEstado(String estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);
        List<PedidoResponseDTO> result = new ArrayList<>(pedidos.size());
        for (Pedido pedido : pedidos) {
            List<Articulo> articulos = pedido.getArticulos();
            List<String> articulosNumeroSerie = new ArrayList<>(articulos.size());
            for (Articulo articulo : articulos) {
                articulosNumeroSerie.add(articulo.getNumeroSerie());
            }
            result.add(new PedidoResponseDTO(
                    pedido.getId(),
                    pedido.getFechaPedido(),
                    pedido.getEstado(),
                    pedido.getCliente().getDni(),
                    articulosNumeroSerie));
        }
        return result;
    }

    public List<PedidoResponseDTO> findByFecha(String fecha) {
        LocalDate date = LocalDate.parse(fecha);
        LocalDateTime inicio = date.atStartOfDay();
        LocalDateTime fin = date.atTime(LocalTime.MAX);
        List<Pedido> pedidos = pedidoRepository.findByFechaPedidoBetween(inicio, fin);
        List<PedidoResponseDTO> result = new ArrayList<>(pedidos.size());
        for (Pedido pedido : pedidos) {
            List<Articulo> articulos = pedido.getArticulos();
            List<String> articulosNumeroSerie = new ArrayList<>(articulos.size());
            for (Articulo articulo : articulos) {
                articulosNumeroSerie.add(articulo.getNumeroSerie());
            }
            result.add(new PedidoResponseDTO(
                    pedido.getId(),
                    pedido.getFechaPedido(),
                    pedido.getEstado(),
                    pedido.getCliente().getDni(),
                    articulosNumeroSerie));
        }
        return result;
    }

    public PedidoResponseDTO findUltimoPedido() {
        Pedido pedido = pedidoRepository.findFirstByOrderByFechaPedidoDesc()
                .orElseThrow(() -> new PedidoNotFoundException("No hay pedidos registrados"));
        List<Articulo> articulos = pedido.getArticulos();
        List<String> articulosNumeroSerie = new ArrayList<>(articulos.size());
        for (Articulo articulo : articulos) {
            articulosNumeroSerie.add(articulo.getNumeroSerie());
        }
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getEstado(),
                pedido.getCliente().getDni(),
                articulosNumeroSerie);
    }

    public List<PedidoResponseDTO> findNoCancelados() {
        List<Pedido> pedidos = pedidoRepository.findByEstadoNot("CANCELADO");
        List<PedidoResponseDTO> result = new ArrayList<>(pedidos.size());
        for (Pedido pedido : pedidos) {
            List<Articulo> articulos = pedido.getArticulos();
            List<String> articulosNumeroSerie = new ArrayList<>(articulos.size());
            for (Articulo articulo : articulos) {
                articulosNumeroSerie.add(articulo.getNumeroSerie());
            }
            result.add(new PedidoResponseDTO(
                    pedido.getId(),
                    pedido.getFechaPedido(),
                    pedido.getEstado(),
                    pedido.getCliente().getDni(),
                    articulosNumeroSerie));
        }
        return result;
    }

    public List<PedidoResponseDTO> findExcepto(String estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstadoNot(estado);
        List<PedidoResponseDTO> result = new ArrayList<>(pedidos.size());
        for (Pedido pedido : pedidos) {
            List<Articulo> articulos = pedido.getArticulos();
            List<String> articulosNumeroSerie = new ArrayList<>(articulos.size());
            for (Articulo articulo : articulos) {
                articulosNumeroSerie.add(articulo.getNumeroSerie());
            }
            result.add(new PedidoResponseDTO(
                    pedido.getId(),
                    pedido.getFechaPedido(),
                    pedido.getEstado(),
                    pedido.getCliente().getDni(),
                    articulosNumeroSerie));
        }
        return result;
    }

    public Long contarPorEstado(String estado) {
        return pedidoRepository.countByEstado(estado);
    }

}
