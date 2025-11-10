package com.example.Guiada.component;

import org.springframework.stereotype.Component;

@Component
public class MotorGasolina implements  Motor{
    public String getTipo(){
        return "Motor de gasolina";
    }
    public int getPotencia(){
        return 120;
    }
}
