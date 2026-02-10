package com.example.Actividad_Respaso.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="pistas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Pista {

    @Id
    private String nombre;

    @Column
    private String ubicacion;

    @Column
    private String tipo;

    /*Pista-1:N-Reservas*/
    //@OneToMany
}
