package com.example.compras.components;


import com.example.compras.dto.CarritoDTO;
import com.example.compras.dto.FacturaDTO;
import org.springframework.stereotype.Component;

public interface IPromocionStrategy {

    public FacturaDTO calcular(CarritoDTO carrito);
}
