package com.example.Compra_Periodos.strategies;

import com.example.Compra_Periodos.dto.CarritoDTO;
import com.example.Compra_Periodos.dto.FacturaDTO;
import com.example.Compra_Periodos.dto.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("BLACK_FRIDAY")
public class BlackFridayStrategy implements IPromocionStrategy {

    @Override
    public FacturaDTO calcular(CarritoDTO carrito) {
        double subtotal = 0;
        double descuento = 0;
        double total = 0;
        double envio = 10;
        List<String> desglose = new ArrayList<>();
        double pesototal = 0;

        for(ItemDTO i : carrito.getItems()){

        };
    }
}
