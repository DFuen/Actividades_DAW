package com.example.Actividad5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name="lineapedido")
public class LineaPedido {

    @Id
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer cantidad;

    @Column(nullable = false)
    @NotNull
    @Positive
    @Digits(integer = 9, fraction = 2)
    private double precioCompra;


}
