package com.example.Actividad1.dto;

public class PorcentajeDTO {
    private int horasOcupadas;
    private int horasLibres;
    private float porcentajeOcupacion;

    public int getHorasOcupadas() {
        return horasOcupadas;
    }

    public void setHorasOcupadas(int horasOcupadas) {
        this.horasOcupadas = horasOcupadas;
    }

    public int getHorasLibres() {
        return horasLibres;
    }

    public void setHorasLibres(int horasLibres) {
        this.horasLibres = horasLibres;
    }

    public float getPorcentajeOcupacion() {
        return porcentajeOcupacion;
    }

    public void setPorcentajeOcupacion(float porcentajeOcupacion) {
        this.porcentajeOcupacion = porcentajeOcupacion;
    }
}
