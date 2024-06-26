package com.ecommerce.springecommerce.service;

import com.ecommerce.springecommerce.model.Producto;

import java.util.Optional;

//interface donde definimos los metodos crud
// en la interfas solo se define los metodos
public interface ProductoService {
    //este metodo retorna un (Producto), se llama(save), este metodo recibe un objeto de tipo (Producto)
    public Producto save(Producto producto);
    //metodo obtener un producto
    /*aqui nos retorna un (Optional) de tipo (Producto)
    * se utiliza Optional para validar si lo que llamamos a la bd existe o no
    * el nombre es get y lo que le pasamos es id que va hacer el id del producto a buscar*/
    public Optional<Producto> get(Integer id);
    //metodo actualizar, void porque no nos retorna nada
    public void update(Producto producto);
    //metodo para eliminar
    //le pasamos el id del producto a eliminar
    public void delete(Integer id);
}
