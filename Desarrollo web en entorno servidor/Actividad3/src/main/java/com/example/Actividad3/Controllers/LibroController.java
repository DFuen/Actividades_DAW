package com.example.Actividad3.Controllers;

import com.example.Actividad3.models.Libro;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class LibroController {

    private List<Libro> libros = new ArrayList<>(Arrays.asList(
            new Libro("Cien años de soledad",1,"Gabriel García Márquez"),
            new Libro("Don Quijote de la Mancha",2,"Miguel de Cervantes"),
            new Libro("Don Quijote de la Mancha",3, "Carlos Ruiz Zafón")
    ));

    @GetMapping("/")
    public String listarLibros(Model model) {
        model.addAttribute("libros", libros);
        return "listaLibros";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable int id, Model model) {
        Libro libro = libros.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("libro", libro);
        return "formularioEditar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarLibro(@PathVariable int id, @ModelAttribute Libro libroActualizado) {
        for (Libro l : libros) {
            if (l.getId() == id) {
                l.setTitulo(libroActualizado.getTitulo());
                l.setAutor(libroActualizado.getAutor());
                break;
            }
        }
        return "redirect:/";
    }
}
