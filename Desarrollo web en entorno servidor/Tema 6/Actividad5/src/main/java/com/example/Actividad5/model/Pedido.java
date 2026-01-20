package com.example.Actividad5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="pedido")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @NotNull
    @PastOrPresent
    private LocalDate fechaPedido;

    @Column(nullable = false)
    @NotNull
    @Pattern(regexp = "Pendiente|Enviado|Cancelado")
    private String estado;

    @Column
    private Cliente cliente;
}
