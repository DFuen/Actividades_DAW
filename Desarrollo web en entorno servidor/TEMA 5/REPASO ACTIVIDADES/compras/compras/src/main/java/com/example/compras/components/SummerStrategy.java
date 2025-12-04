package com.example.compras.components;

import com.example.compras.dto.CarritoDTO;
import com.example.compras.dto.FacturaDTO;
import com.example.compras.dto.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("SUMMER")
public class SummerStrategy implements IPromocionStrategy{

    @Override
    public FacturaDTO calcular(CarritoDTO carrito) {

        double subtotal = 0;
        double descuento = 0;
        double total = 0;
        double envio = 5;
        List<String> desglose = new ArrayList<>();
        double pesototal = 0;

        List<Double> comida = new ArrayList<>();
        int ncomida = 0;

        for(ItemDTO i : carrito.getItems()){
            if(i.getCategoria().equals("ROPA")){
                descuento += (i.getCantidad() * i.getPrecioUnitario()) * 0.2;
                subtotal += (i.getCantidad() * i.getPrecioUnitario()) * 0.8;
            }else if(i.getCategoria().equals("JUGUETES")) {
                descuento += (i.getCantidad() * i.getPrecioUnitario()) * 0.15;
                subtotal += (i.getCantidad() * i.getPrecioUnitario()) * 0.85;

            }else if(i.getCategoria().equals("COMIDA")){
                subtotal += (i.getCantidad() * i.getPrecioUnitario());
                comida.add(i.getPrecioUnitario());
                ncomida += i.getCantidad();
            }else{
                descuento += (i.getCantidad() * i.getPrecioUnitario());
                subtotal += (i.getCantidad() * i.getPrecioUnitario());
            }
        }

        if(ncomida >= 2){
            comida.stream().sorted();
            System.out.println(comida);
            subtotal = subtotal - comida.get(1);
        }

        total = subtotal + envio;

        return new FacturaDTO("Summer", subtotal, descuento, envio, total, desglose);
    }
}
