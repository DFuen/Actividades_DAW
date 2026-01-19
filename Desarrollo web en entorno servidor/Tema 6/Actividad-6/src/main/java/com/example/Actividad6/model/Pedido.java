package com.example.Actividad6.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
@Entity
@Table(name="pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "estado('Pendiente', 'Enviado', 'Cancelado')")
public class Pedido {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, updatable = false)
    @NotNull
    @PastOrPresent
    private LocalDate fechaPedido;

    @Column(nullable = false)
    @NotNull
    private String estado;
}
