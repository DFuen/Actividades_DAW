package com.example.Actividad3.Controllers;

import com.example.Actividad3.models.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductoController {

    private List<Producto> productos = Arrays.asList(
            new Producto(1, "Laptop", 1200.0, "Laptop de 16GB RAM y SSD 512GB"),
            new Producto(2, "Mouse", 25.0, "Mouse inalámbrico ergonómico"),
            new Producto(3, "Teclado", 45.0, "Teclado mecánico retroiluminado")
    );

    @GetMapping("/productos")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productos);
        return "lista";
    }

    @GetMapping("/producto/{id}")
    public String detalleProducto(@PathVariable int id, Model model) {
        Producto encontrado = productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("producto", encontrado);
        return "detalle";
    }
}
