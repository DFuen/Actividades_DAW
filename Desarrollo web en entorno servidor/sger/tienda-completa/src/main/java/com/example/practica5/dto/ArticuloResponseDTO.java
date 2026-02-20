package com.example.practica5.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloResponseDTO {

    private String numeroSerie;
    private String nombreProducto;
    private BigDecimal precioVenta;
    private Integer stockDisponible;
}
