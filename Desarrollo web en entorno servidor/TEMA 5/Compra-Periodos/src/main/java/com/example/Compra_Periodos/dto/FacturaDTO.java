package com.example.Compra_Periodos.dto;

import java.util.List;

public class FacturaDTO {
    private String periodoAplicado;
    private double subtotal;
    private float descuentoTotal;
    private float gastoEnvio;
    private double totalPagar;
    private List<String> desglose;

    public FacturaDTO() {
    }

    public FacturaDTO(double subtotal, double descuento, double total) {
    }

    public FacturaDTO(String standard, double subtotal, double descuento, double envio, double total, List<String> desglose) {
    }

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

    public float getDescuentoTotal() {
        return descuentoTotal;
    }

    public void setDescuentoTotal(float descuentoTotal) {
        this.descuentoTotal = descuentoTotal;
    }

    public float getGastoEnvio() {
        return gastoEnvio;
    }

    public void setGastoEnvio(float gastoEnvio) {
        this.gastoEnvio = gastoEnvio;
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
