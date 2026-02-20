package com.example.practica5.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos", uniqueConstraints = {})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @PastOrPresent
    @Column(name = "fecha_pedido", nullable = false, updatable = false)
    private LocalDateTime fechaPedido;

    @NotBlank
    @Pattern(regexp = "^(PENDIENTE|ENVIADO|CANCELADO)$")
    @Column(name = "estado", nullable = false)
    private String estado;

    // Relación Many-to-One con Cliente
    @ManyToOne
    @JoinColumn(name = "dni_cliente", nullable = false)
    private Cliente cliente;

    // Relación Many-to-Many con Articulo
    @ManyToMany
    @JoinTable(
        name = "lineas_pedido",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "articulo_numero_serie")
    )
    private List<Articulo> articulos;
}
