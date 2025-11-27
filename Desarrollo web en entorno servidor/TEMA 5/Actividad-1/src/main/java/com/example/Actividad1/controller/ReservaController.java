package com.example.Actividad1.controller;

import com.example.Actividad1.dto.ReservaDTO;
import com.example.Actividad1.model.Reserva;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ReservaController {

    private List<Reserva> listaReservas = new ArrayList<>();

    private int horaInicioDia=9;
    private int horaFinalDia=20;

    @GetMapping("/reservas")
    public ResponseEntity<List<Reserva>> verReservas(){
        return new ResponseEntity<>(listaReservas,HttpStatus.OK);
    }

    @PostMapping("/añadirReserva")
    public ResponseEntity<?> crearReserva(@RequestBody ReservaDTO reservaDTO){
        int inicio = reservaDTO.getHoraInicio();
        int duracion = reservaDTO.getDuracionHoras();
        String nombre = reservaDTO.getNombre();

        if(inicio==null || duracion==null || nombre == null || nombre.trim().isEmpty() || duracion <= 0){

        }
    }

}
