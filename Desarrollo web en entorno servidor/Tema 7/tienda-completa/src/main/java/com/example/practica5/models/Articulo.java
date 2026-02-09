package com.example.practica5.models;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @Size(max = 50, message = "El número de serie no puede exceder 50 caracteres")
    @Column(name = "numero_serie", length = 50, nullable = false)
    private String numeroSerie;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 5, message = "El nombre del producto debe tener al menos 5 caracteres")
    @Column(name = "nombre_producto", unique = true, nullable = false)
    private String nombreProducto;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos y 2 decimales")
    @Column(name = "precio_venta", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioVenta;

    @NotNull(message = "El stock disponible es obligatorio")
    @Min(value = 0, message = "El stock no puede ser menor a 0")
    @Column(name = "stock_disponible", nullable = false)
    private Integer stockDisponible;

    // Relación Many-to-Many con Pedido
    @ManyToMany(mappedBy = "articulos")
    @JsonIgnoreProperties("articulos")
    private List<Pedido> pedidos;

    //Marca un constructor para que Jackson lo use al deserializar
    @JsonCreator
    public Articulo(@JsonProperty("numeroSerie") String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }
}
