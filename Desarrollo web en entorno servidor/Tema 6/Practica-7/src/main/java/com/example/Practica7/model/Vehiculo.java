package com.example.Practica7.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "vehiculo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String marca;

    @Column
    private String modelo;

    @Column
    private Integer anio;

    @Column
    private Integer precio;

    @Column
    private boolean disponible;
}
