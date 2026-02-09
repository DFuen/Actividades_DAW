package com.example.practica5.excepcions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClienteNotFoundExcepcion extends ResponseStatusException {
    public ClienteNotFoundExcepcion(String mensaje){
        super(HttpStatus.NOT_FOUND,mensaje);
    }
}
