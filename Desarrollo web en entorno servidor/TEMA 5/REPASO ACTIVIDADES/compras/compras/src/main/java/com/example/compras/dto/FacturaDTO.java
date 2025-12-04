package com.example.compras.dto;

import java.util.List;

public class FacturaDTO {

    private String periodoAplicado;
    private double subtotal;
    private double descuentoTotal;
    private double gastosEnvio;
    private double totalPagar;
    private List<String> desglose;


    public FacturaDTO(String periodoAplicado, double subtotal, double descuentoTotal, double gastosEnvio, double totalPagar, List<String> desglose) {
        this.periodoAplicado = periodoAplicado;
        this.subtotal = subtotal;
        this.descuentoTotal = descuentoTotal;
        this.gastosEnvio = gastosEnvio;
        this.totalPagar = totalPagar;
        this.desglose = desglose;
    }

    public FacturaDTO(){}

    public String getPeriodoAplicado() {
        return periodoAplicado;
    }

    public void setPeriodoAplicado(String periodoAplicado) {
        this.periodoAplicado = periodoAplicado;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuentoTotal() {
        return descuentoTotal;
    }

    public void setDescuentoTotal(double descuentoTotal) {
        this.descuentoTotal = descuentoTotal;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public List<String> getDesglose() {
        return desglose;
    }

    public void setDesglose(List<String> desglose) {
        this.desglose = desglose;
    }
}
