package com.example.Tienda.model;

import jakarta.persistence.*;

@Entity
@Table(name="lineapedido")
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public LineaPedido() {}

    public LineaPedido(Long id) {
        this.id = id;
    }
}
