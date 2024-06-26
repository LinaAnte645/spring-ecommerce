package com.ecommerce.springecommerce.repository;

import com.ecommerce.springecommerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//en como un DAO
//esta interfaz nos obtiene los metodos CRUD que nos proporciona JPA
//extiende a Jpa y le indicamos a que tabla va utilizar, al igual el tipo de dato del id
//Repository para poderla inyectar esta interfax en el service
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
