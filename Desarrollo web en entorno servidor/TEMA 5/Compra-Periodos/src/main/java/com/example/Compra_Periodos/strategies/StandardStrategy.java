package com.example.Compra_Periodos.strategies;

import com.example.Compra_Periodos.dto.CarritoDTO;
import com.example.Compra_Periodos.dto.FacturaDTO;
import com.example.Compra_Periodos.dto.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("STANDARD")
public class StandardStrategy implements IPromocionStrategy {

    @Override
    public FacturaDTO calcular(CarritoDTO carrito) {

        double subtotal = 0;
        double descuento = 0;
        double total = 0;
        double envio = 10;
        List<String> desglose = new ArrayList<>();
        double pesototal = 0;

        for(ItemDTO i : carrito.getItems()){
            pesototal +=i.getCantidad() * i.getPesoKg();
            subtotal +=i.getPrecioUnitario() * i.getCantidad();
        };

        /*CONDICIÓN PARA DESCUENTO DE SUPERIOR A 1000€*/
        if(subtotal >1000){
            descuento = subtotal * 0.05;/*SACAMOS EL DESCUENTO TOTAL*/
            subtotal = subtotal * 0.95;/*SACAMOS EL SUBTOTAL CON EL DESCUENTO APLICADO*/
        }

        /*CODICIÓN PARA EL PESO MAYOR A 20KG*/
        if(pesototal >20){
            envio = 20;
        }

        total = subtotal + envio;

        desglose.add("Todo correcto");

        return new FacturaDTO("Standard", subtotal,descuento,envio,total,desglose);
    }
}
