package com.example.Practica3.dto;

public class MovimientoDTO {
    private String direccion;
    private String tokenSeguridad;

    public MovimientoDTO() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTokenSeguridad() {
        return tokenSeguridad;
    }

    public void setTokenSeguridad(String tokenSeguridad) {
        this.tokenSeguridad = tokenSeguridad;
    }
}
