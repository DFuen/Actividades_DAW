package com.example.Actividad_Respaso.controllers;

import com.example.Actividad_Respaso.services.UsuariosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    UsuariosServices usuariosServices;

    @PostMapping("usuario/registro")
    public
}
