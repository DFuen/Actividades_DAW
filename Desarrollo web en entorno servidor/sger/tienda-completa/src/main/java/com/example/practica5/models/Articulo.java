package com.example.practica5.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "articulos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {

    @Id
    @Size(max = 50)
    @Column(name = "numero_serie", length = 50, nullable = false)
    private String numeroSerie;

    @NotBlank
    @Size(min = 5)
    @Column(name = "nombre_producto", unique = true, nullable = false)
    private String nombreProducto;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 10, fraction = 2)
    @Column(name = "precio_venta", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioVenta;

    @NotNull
    @Min(value = 0)
    @Column(name = "stock_disponible", nullable = false)
    private Integer stockDisponible;

    // Relación Many-to-Many con Pedido
    @ManyToMany(mappedBy = "articulos")
    private List<Pedido> pedidos;

}
