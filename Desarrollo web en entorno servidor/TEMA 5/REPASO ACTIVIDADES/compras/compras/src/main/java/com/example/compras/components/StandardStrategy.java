package com.example.compras.components;

import com.example.compras.dto.CarritoDTO;
import com.example.compras.dto.FacturaDTO;
import com.example.compras.dto.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("STANDARD")
public class StandardStrategy implements IPromocionStrategy{

    @Override
    public FacturaDTO calcular(CarritoDTO carrito) {

        double subtotal = 0;
        double descuento = 0;
        double total = 0;
        double envio = 10;
        List<String> desglose = new ArrayList<>();
        double pesototal = 0;

        for(ItemDTO p : carrito.getItems()){
            pesototal += p.getCantidad() * p.getPesoKg();
            subtotal += p.getPrecioUnitario() * p.getCantidad();
        };

        if(subtotal > 1000){
            descuento = subtotal * 0.05;
            subtotal = subtotal * 0.95;
        }

        if(pesototal > 20){
            envio = 20;
        }

        total = subtotal + envio;

        desglose.add("Todo correcto");


        return new FacturaDTO("Standard", subtotal, descuento,envio,total, desglose);
    }
}
