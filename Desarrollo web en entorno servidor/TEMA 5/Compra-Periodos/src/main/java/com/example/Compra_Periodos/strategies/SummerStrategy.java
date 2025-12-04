package com.example.Compra_Periodos.strategies;

import com.example.Compra_Periodos.dto.CarritoDTO;
import com.example.Compra_Periodos.dto.FacturaDTO;
import com.example.Compra_Periodos.dto.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("SUMMER")
public class SummerStrategy implements IPromocionStrategy {

    @Override
    public FacturaDTO calcular(CarritoDTO carrito) {
        double subtotal = 0;
        double descuento = 0;
        double total = 0;
        double envio = 10;
        List<String> desglose = new ArrayList<>();
        double pesototal = 0;

        List<String> comida = new ArrayList<>();
        List<Double> precio = new ArrayList<>();

        for(ItemDTO i : carrito.getItems()){
            if( i.getCategoria().equalsIgnoreCase("COMIDA")) {
                comida.add(i.getNombre());
                precio.add(i.getPrecioUnitario());
            }
            if(i.getCategoria().equalsIgnoreCase("JUGUETES")){
                subtotal +=i.getPrecioUnitario()*i.getCantidad()*0.85;
            }
            if(i.getCategoria().equalsIgnoreCase("ROPA")){
                subtotal +=(i.getPrecioUnitario()*i.getCantidad())*0.8;
            }
            }

        };
}
