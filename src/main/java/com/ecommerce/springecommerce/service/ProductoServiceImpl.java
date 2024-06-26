package com.ecommerce.springecommerce.service;

import com.ecommerce.springecommerce.model.Producto;
import com.ecommerce.springecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
//en esta clase implementa los metodos de la interfaz ProductoService
//la declaramos como Service nos permita inyectar en el controller esta clase ProductoServiceImpl
//para hacer el llamado directamente a los metodos crud
@Service
public class ProductoServiceImpl implements ProductoService{
    //Autowired indica que estamos inyectando a esta clase un objeto
    @Autowired
    //declaramos un objeto de tipo
    private ProductoRepository productoRepository;
    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }
}
