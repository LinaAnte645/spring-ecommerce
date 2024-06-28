package com.ecommerce.springecommerce.controller;

import com.ecommerce.springecommerce.model.Producto;
import com.ecommerce.springecommerce.model.Usuario;
import com.ecommerce.springecommerce.service.ProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/productos")
public class ProductoController {
    //una variable de tipo logger, nos hace un logo de lo que se va ejecutando
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    @Autowired
    private ProductoService productoService;
    //direccionamos hacia la vista
    @GetMapping("")
    public String show() {
        return "productos/show";
    }
    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }
    @PostMapping("/save")
    public String save(Producto producto) {
        LOGGER.info("Este e el objeto producto  {}",producto);
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);
        productoService.save(producto);
        return "redirect:/productos";
    }
}
