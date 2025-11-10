package com.example.Guiada.component;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class MotorHibrido  implements Motor{
    public String getTipo(){
        return "Motor hibrido";
    }
    public int getPotencia(){
        return 250;
    }
}
