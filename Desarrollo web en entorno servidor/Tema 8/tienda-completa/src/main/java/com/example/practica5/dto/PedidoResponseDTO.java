package com.example.practica5.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    private Long id;
    private LocalDateTime fechaPedido;
    private String estado;
    private String clienteDni;
    private List<String> articulosNumeroSerie;
}
