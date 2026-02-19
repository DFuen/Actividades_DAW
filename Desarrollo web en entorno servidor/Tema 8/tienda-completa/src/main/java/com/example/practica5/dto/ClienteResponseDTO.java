package com.example.practica5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    private String dni;
    private String nombre;
    private String apellidos;
    private String correo;
    private String direccion;
}
