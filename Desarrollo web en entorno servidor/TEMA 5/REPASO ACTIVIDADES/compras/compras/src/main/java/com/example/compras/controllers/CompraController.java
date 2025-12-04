package com.example.compras.controllers;

import com.example.compras.components.IPromocionStrategy;
import com.example.compras.dto.CarritoDTO;
import com.example.compras.dto.FacturaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CompraController {

    @Autowired
    Map<String, IPromocionStrategy> estrategia = new HashMap<>();

    @PostMapping("/compra")
    public ResponseEntity<?> calculo(@RequestParam String periodo,
                                     @RequestBody CarritoDTO carrito){

    if(!estrategia.containsKey(periodo.toUpperCase())){
        return ResponseEntity.badRequest().body("Estrategia no válida");
    }

        FacturaDTO factura = estrategia.get(periodo.toUpperCase()).calcular(carrito);

    return ResponseEntity.ok().body(factura);
    }

}
