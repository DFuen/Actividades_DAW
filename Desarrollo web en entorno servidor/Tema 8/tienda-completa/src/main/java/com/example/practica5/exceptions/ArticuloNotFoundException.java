package com.example.practica5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ArticuloNotFoundException extends ResponseStatusException {
    public ArticuloNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
