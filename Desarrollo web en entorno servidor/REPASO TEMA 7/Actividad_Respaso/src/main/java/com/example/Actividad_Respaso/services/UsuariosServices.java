package com.example.Actividad_Respaso.services;

import com.example.Actividad_Respaso.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariosServices {

    @Autowired
    UsuarioRepository usuarioRepository;
}
