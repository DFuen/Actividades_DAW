package com.example.Tienda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="articulo")
public class Articulo {

    @Column
    private Integer numeroSerie;


    public Articulo() {
    }

    public Articulo(Integer numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Integer getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(Integer numeroSerie) {
        this.numeroSerie = numeroSerie;
    }
}
