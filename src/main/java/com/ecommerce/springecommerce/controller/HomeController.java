package com.ecommerce.springecommerce.controller;

import com.ecommerce.springecommerce.model.DetalleOrden;
import com.ecommerce.springecommerce.model.Orden;
import com.ecommerce.springecommerce.model.Producto;
import com.ecommerce.springecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {
    //variable que nos permite imprimir
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    //objeto para obtener todos los productos
    @Autowired
    private ProductoService productoService;
    /*lista para almacenar los detalles de la orden(el carrito)*/
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
    //datos de la orden
    Orden orden = new Orden();

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

    /**/
    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;
        //nos obtiene el producto
        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto añadido:  {}", optionalProducto.get());
        log.info("Cantidad:  {}", cantidad);
        producto = optionalProducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        //poner el id
        detalleOrden.setProducto(producto);
        //validar que el producto no se añada mas de una vez
        Integer idProducto = producto.getId();
        //funcion tipo landa que funciona como un for, nos ayuda a verificar si el id añadido ya se encuentra en la lista
        boolean ingresado=detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);
        if(!ingresado) {
            //añadir cada producto a la lista
            detalles.add(detalleOrden);
        }

        //sumar el total de los productos que añada al carrito, utilizamos una funcion landa,
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }

    //metodo para quitar producto de la ista del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model) {
        //lista nueva de productos
        List<DetalleOrden> ordenNueva = new ArrayList<DetalleOrden>();
        for (DetalleOrden detalleOrden : detalles) {
            //si es diferente al id que vamos a eliminar lo añade a la lista
            if (detalleOrden.getProducto().getId() != id) {
                ordenNueva.add(detalleOrden);
            }
        }
        //agregar la lista nueva
        detalles = ordenNueva;
        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }
    //metodo
    @GetMapping("/getCart")
    public String getCart(Model model) {
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "/usuario/carrito";
    }
}


