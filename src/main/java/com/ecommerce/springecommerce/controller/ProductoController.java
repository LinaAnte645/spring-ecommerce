package com.ecommerce.springecommerce.controller;

import com.ecommerce.springecommerce.model.Producto;
import com.ecommerce.springecommerce.model.Usuario;
import com.ecommerce.springecommerce.service.ProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/productos")
public class ProductoController {
    //una variable de tipo logger, nos hace un logo de lo que se va ejecutando
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    //
    @Autowired
    private ProductoService productoService;
    //direccionamos hacia la vista
    /*el objeto Model lleva informacion desde backend asi la vita
    Model (va a llevar la lista de objetos hacia la vista show)
    aqui invocamos la metodo addAttribute que le pasamos 2 parametros
    1: nombre con el que vamos a recibir la informacion
    2: pasamos le objeto que vamos a enviar a la vista
    * */
    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos",productoService.findAll());
        return "productos/show";
    }
    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }
    @PostMapping("/save")
    public String save(Producto producto) {
        LOGGER.info("Este e el objeto producto  {}",producto);
        //el producto requiere un usuario para ser registrado
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        //añadimos el usuario
        producto.setUsuario(u);
        productoService.save(producto);
        return "redirect:/productos";
    }
}
