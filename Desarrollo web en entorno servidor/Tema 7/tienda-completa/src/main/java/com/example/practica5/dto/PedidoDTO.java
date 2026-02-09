package com.example.practica5.dto;

import java.time.LocalDateTime;

public class PedidoDTO {

    private LocalDateTime fechaPedido;
    private String estado;
    private ClienteDTO cliente;

    public PedidoDTO() {}

    public PedidoDTO(LocalDateTime fechaPedido, String estado, ClienteDTO cliente) {
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.cliente = cliente;
    }

    public LocalDateTime getFechaPedido() {return fechaPedido;}

    public void setFechaPedido(LocalDateTime fechaPedido) {this.fechaPedido = fechaPedido;}

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}

    public ClienteDTO getCliente() {return cliente;}

    public void setCliente(ClienteDTO cliente) {this.cliente = cliente;}
}
