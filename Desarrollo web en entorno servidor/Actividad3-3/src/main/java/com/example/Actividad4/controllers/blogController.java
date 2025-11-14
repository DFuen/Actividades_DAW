package com.example.Actividad4.controllers;

import com.example.Actividad4.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class blogController {
    private List<Post> post = new ArrayList<>();

    @GetMapping("/")
    public String mostrarindex(Model model){
        return "index";
    }
    @GetMapping ("/blog")
        public String mostrarformulario(Model model){
        model.addAttribute("p",new Post());
        return "formulario";
    }
    @PostMapping("/guardar")
        public String añadirBlog(Model model,@ModelAttribute Post p){
        post.add(p);
        model.addAttribute("Lista",post);
        return "index";
    }
}
