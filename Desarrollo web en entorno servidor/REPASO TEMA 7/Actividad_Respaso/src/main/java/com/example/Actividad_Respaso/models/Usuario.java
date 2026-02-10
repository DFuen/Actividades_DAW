package com.example.Actividad_Respaso.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Usuario {

    @Id
    private String email;

    @Column
    private String nombre;

    @Column
    private String password;

    @Column
    private LocalDateTime fechaRegistro;

    /*USUARIO-1:N-RESERVAS*/
    //@OneToMany



}
