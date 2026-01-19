package com.example.Actividad6.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @Column
    @NotBlank
    @Pattern(regexp = "^[0-9]{8}-[A-Z]{1}$")
    private String dni;

    @Column
    @NotBlank
    @Size(min = 50)
    private String nombre;

    @Column
    @NotBlank
    @Size(min = 100)
    private String apellidos;


    @Column(nullable = false, unique = true)
    @NotNull
    @Email
    private String correo;

    @Column(nullable = false)
    @NotNull
    @Size(max = 255)
    private String direccion;
}
