package com.example.Compra_Periodos.controller;

import com.example.Compra_Periodos.dto.CarritoDTO;
import com.example.Compra_Periodos.strategies.IPromocionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public class CompraController {
    @Autowired
    public Map<String, IPromocionStrategy> estrategias;

    @PostMapping
    public ResponseEntity<?> procesarPedido(
            @RequestParam String periodo,
            @RequestBody CarritoDTO carrito) {

        if (periodo == null) {
            return ResponseEntity.badRequest().body("El parametro periodo no existe, PARAMETROS");
        }

    }
}
