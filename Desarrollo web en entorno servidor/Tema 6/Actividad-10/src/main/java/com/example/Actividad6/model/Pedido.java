package com.example.Actividad6.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name="id_cliente")
    @JsonIgnoreProperties("pedidos")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name="pedido_articulos",
            joinColumns = @JoinColumn(name="id_pedido"),
            inverseJoinColumns = @JoinColumn(name="id_articulo")
    )
    @JsonIgnoreProperties("pedidos")
    private List<Articulo> articulos;

}
