package com.example.Compra_Periodos.strategies;

import com.example.Compra_Periodos.dto.CarritoDTO;
import com.example.Compra_Periodos.dto.FacturaDTO;

public interface IPromocionStrategy {

    FacturaDTO calcular(CarritoDTO carrito);

}
