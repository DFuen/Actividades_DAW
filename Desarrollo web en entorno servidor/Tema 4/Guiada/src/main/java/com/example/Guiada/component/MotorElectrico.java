package com.example.Guiada.component;

import org.springframework.stereotype.Component;

@Component("motorElectrico")//Es opcional para dar el nombre de diferente forma por defecto es en minuscula
public class MotorElectrico implements Motor{
    public String getTipo(){
        return  "Motor electrico";
    }
    public int getPotencia(){
        return 200;
    }
}
