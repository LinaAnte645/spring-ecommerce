package com.ecommerce.springecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    //direccionamos hacia la vista
    @GetMapping("")
    public String show() {
        return "productos/show";
    }
}
