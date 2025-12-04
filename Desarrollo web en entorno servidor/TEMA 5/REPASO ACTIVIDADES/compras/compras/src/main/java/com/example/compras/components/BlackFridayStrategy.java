package com.example.compras.components;

import com.example.compras.dto.CarritoDTO;
import com.example.compras.dto.FacturaDTO;
import com.example.compras.dto.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("BLACK_FRIDAY")
public class BlackFridayStrategy implements IPromocionStrategy{

    @Override
    public FacturaDTO calcular(CarritoDTO carrito) {

        double subtotal = 0;
        double descuento = 0;
        double total = 0;
        double envio = 0;
        List<String> desglose = new ArrayList<>();
        double pesototal = 0;

        for(ItemDTO p : carrito.getItems()){

            for(int i = 1 ; i <= p.getCantidad();i++){
                if(i%2==0){
                    descuento += p.getPrecioUnitario() * 0.5;
                    subtotal += p.getPrecioUnitario() * 0.5;

                }else{
                    subtotal += p.getPrecioUnitario();
                }
            }

        }

        if(carrito.getItems().size() > 5){
            descuento = subtotal * 0.1;
            subtotal = subtotal * 0.9;
        }

        total = subtotal;

        return new FacturaDTO("Black Friday", subtotal, descuento, envio, total, desglose);
    }
}
