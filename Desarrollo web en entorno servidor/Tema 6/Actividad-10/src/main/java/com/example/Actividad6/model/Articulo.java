package com.example.Actividad6.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "articulo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {

    @Id
    private Long numeroSerie;

    @Column(nullable = false)
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{5}$")
    private String nombreProducto;

    @Column(nullable = false)
    @NotNull
    @Digits(integer = 8,fraction = 2)
    @Min(0)
    private BigDecimal precioVenta;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer Stock;

    @ManyToMany(mappedBy = "articulos")
    @JsonIgnoreProperties("articulo")
    private List<Pedido> pedidos;
}
