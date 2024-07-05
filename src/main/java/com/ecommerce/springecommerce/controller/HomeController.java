package com.ecommerce.springecommerce.controller;

import com.ecommerce.springecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    //objeto para obtener todos los productos
    @Autowired
    private ProductoService productoService;
    @GetMapping("")
    public String home(Model model) {
        //pasamos 2 parametros nombre de la variable y pasamos todos lo productos
        model.addAttribute("productos", productoService.findAll());
        return "usuario/home";
    }
}
