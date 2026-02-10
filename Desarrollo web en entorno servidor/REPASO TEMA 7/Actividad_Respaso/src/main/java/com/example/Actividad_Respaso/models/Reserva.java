package com.example.Actividad_Respaso.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="pistas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Reserva {

    @Id
    private String email;
    private String nombre;

    @Column
    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    /*RESEVA-N:1-USUARIO*/
    //@ManyToOne
    /*RESERVA-N:1-PISTA*/
    //@ManyToOne
}
