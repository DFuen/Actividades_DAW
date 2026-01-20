package com.example.Actividad5.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="cliente")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Cliente {

    @Id
    @Column
    private String DNI;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String nombre;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @Size(max = 100)
    private String apellidos;

    @Column(unique = true)
    @Email
    @NotNull
    private String correoElectronico;

    @Column(nullable = false, length = 255)
    private String Direccion;
}

