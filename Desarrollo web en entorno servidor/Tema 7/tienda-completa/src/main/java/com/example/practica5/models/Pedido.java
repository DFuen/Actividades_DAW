package com.example.practica5.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @NotNull(message = "La fecha del pedido es obligatoria")
    @PastOrPresent(message = "La fecha del pedido no puede ser en el futuro")
    @Column(name = "fecha_pedido", nullable = false, updatable = false)
    private LocalDateTime fechaPedido;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(PENDIENTE|ENVIADO|CANCELADO)$", message = "El estado debe ser uno de: PENDIENTE, ENVIADO, CANCELADO")
    @Column(name = "estado", nullable = false)
    private String estado;

    // Relación Many-to-One con Cliente
    @ManyToOne
    @JoinColumn(name = "dni_cliente", nullable = false)
    @JsonIgnoreProperties("pedidos")
    private Cliente cliente;

    // Relación Many-to-Many con Articulo
    @ManyToMany
    @JoinTable(
        name = "lineas_pedido",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "articulo_numero_serie")
    )
    @JsonIgnoreProperties("pedidos")
    private List<Articulo> articulos;
}
