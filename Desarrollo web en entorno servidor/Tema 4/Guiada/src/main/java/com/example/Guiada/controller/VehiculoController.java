package com.example.Guiada.controller;

import com.example.Guiada.component.Motor;
//import com.example.Guiada.component.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class VehiculoController {

//    @Autowired
//    private Vehiculo vehiculo;

    @Autowired
    private Map<String, Motor> tipoMotores;

    @GetMapping("/")
    public String inicio (/*Model model*/) {
//        model.addAttribute("descripcion", vehiculo.getDescripcion());
        return "index";
    }


//    @PostMapping("/vehiculo")
//    public String v(Model model){
//
//    }

    @GetMapping("/api/vehiculo")
    public String getVehiculoInf(Model model){
//        model.addAttribute("tipo", vehiculo.getDescripcion());
        return "detalle";
    }
}
