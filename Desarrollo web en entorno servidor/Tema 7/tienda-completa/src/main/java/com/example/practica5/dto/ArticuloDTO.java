package com.example.practica5.dto;

import java.math.BigDecimal;

public class ArticuloDTO {
    private String nombreProducto;
    private BigDecimal precioVenta;
    private Integer stockDisponible;

    public ArticuloDTO() {}

    public ArticuloDTO(String nombreProducto, BigDecimal precioVenta, Integer stockDisponible) {
        this.nombreProducto = nombreProducto;
        this.precioVenta = precioVenta;
        this.stockDisponible = stockDisponible;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(Integer stockDisponible) {
        this.stockDisponible = stockDisponible;
    }
}
