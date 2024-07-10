package com.ecommerce.springecommerce.controller;

import com.ecommerce.springecommerce.model.Producto;
import com.ecommerce.springecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {
    //variable que nos permite imprimir
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    //objeto para obtener todos los productos
    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String home(Model model) {        //pasamos 2 parametros nombre de la variable y pasamos todos lo productos
        model.addAttribute("productos", productoService.findAll());
        return "usuario/home";
    }

    //este metodo le permite ver la informacion del producto
    //el objeto model nos permite llevar informacion a la vista
    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model) {
        log.info("Id producto enviado como parametro.  {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }
}


