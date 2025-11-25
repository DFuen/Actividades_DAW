package com.example.Actividad1.model;

public class Reserva {
    private int identificador;
    private String nombre;
    private int horaDia;
    private int cantidadHora;

    public Reserva() {
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHoraDia() {
        return horaDia;
    }

    public void setHoraDia(int horaDia) {
        this.horaDia = horaDia;
    }

    public int getCantidadHora() {
        return cantidadHora;
    }

    public void setCantidadHora(int cantidadHora) {
        this.cantidadHora = cantidadHora;
    }
}
