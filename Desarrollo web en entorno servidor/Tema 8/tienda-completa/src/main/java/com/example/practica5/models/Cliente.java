package com.example.practica5.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @NotBlank
    @Size(min = 9, max = 9)
    @Pattern(regexp = "^[0-9]{8}[A-Z]$")
    @Column(name = "dni", length = 9, nullable = false)
    private String dni;

    @NotBlank
    @Size(max = 50)
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotBlank
    @Size(max = 100)
    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;

    @NotNull
    @Email
    @Column(name = "correo", unique = true, nullable = false)
    private String correo;

    @NotBlank
    @Size(max = 255)
    @Column(name = "direccion", length = 255, nullable = false)
    private String direccion;

    // Relación One-to-Many con Pedido
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

}