package com.ecommerce.springecommerce.controller;

import com.ecommerce.springecommerce.model.Producto;
import com.ecommerce.springecommerce.model.Usuario;
import com.ecommerce.springecommerce.service.ProductoService;
import com.ecommerce.springecommerce.service.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Controller
@RequestMapping("/productos")
public class ProductoController {
    //una variable de tipo logger, nos hace un logo de lo que se va ejecutando
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    //Autowired para inyectarla directamente a la clase ProductoController
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UploadFileService upload;
    //Metodo donde direccionamos hacia la vista show
    /*el objeto model lleva informacion desde backend asi la vita
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
    //Metodo donde direccionamos a la vista create
    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }
    //metodo para guardar un producto
    @PostMapping("/save")
    public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("Este e el objeto producto  {}",producto);
        //el producto requiere un usuario para ser registrado
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        //añadimos el usuario
        producto.setUsuario(u);
        //logica para subir la imagen al servidor y guardar en la bd
        //validacion cuando la imagen sea cargada por primera vez
        if(producto.getId()==null) {
            String nombreImagen=upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        productoService.save(producto);
        return "redirect:/productos";
    }
    /*Metodo donde direccionamos  a la vista edit(editar un producto)
    en GetMapping obtenemos el id del registro que vamos a buscar y editar
    PathVariable mapea la variable que viene en la url y la pasa a variable que esta continua a la anotacion PathVariable
    vamos a pasar el objeto a la vista entonces añadimos un objeto de tipo Model, al cual primero declaramos  una variable(qque lleva a la vista ) y le pasamos valor que tiene producto

    * */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        //aqui buscamos el producto
        Optional<Producto> optionalProducto=productoService.get(id);
        producto=optionalProducto.get();
        LOGGER.info("Producto buscado: {}", producto);
        model.addAttribute("producto", producto);
        return "productos/edit";
    }
    //Metodo para actualizar
    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Producto p=new Producto();
        //optenemos el producto, el ultimo get() es para que nos retorne el registro del producto
        p=productoService.get(producto.getId()).get();
        //se edita el producto pero no se carga la imagen por medio del cliente
        if(file.isEmpty()) {

            //se la pasamos nuevamente al producto que estamos editando
            producto.setImagen(p.getImagen());
            //se edita la imagen por medio del cliente, pero hay que eliminar la imagen por defecto
        }else {
            //eliminar cuando no sea la imagen por defecto
            if(!p.getImagen().equals("default.jpg")) {
                //elimina la imagen le pasamos el objeto que hemos optenido
                upload.deleteImage(p.getImagen());
            }
            String nombreImagen=upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }
    //Medoto para eliminar
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        //eliminar la imagen
        Producto p = new Producto();
        //el ultimo get() es para que nos retorne el registro del producto
        p=productoService.get(id).get();
        //eliminar cuando no sea la imagen por defecto
        if(!p.getImagen().equals("default.jpg")) {
            //elimina la imagen le pasamos el objeto que hemos optenido
            upload.deleteImage(p.getImagen());
        }
        productoService.delete(id);
        return "redirect:/productos";
    }
}
