package com.example.Actividad5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="articulo")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Articulo {

    @Id
    @Column(length = 50)
    @Size(max = 50)
    private Integer numeroSerie;

    @Column(nullable = false)
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{5,}$")
    private String nombreProducto;

    @Column(nullable = false)
    @NotNull
    @Digits(integer = 10,fraction = 2)
    @Min(0)
    private double precioVenta;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer stockDisponible;
}