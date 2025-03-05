package com.curso.ecommerce.spring_ecommerce.service;

import java.util.List;

import com.curso.ecommerce.spring_ecommerce.model.Orden;

public interface IOrdenService {
    Orden save(Orden orden); // PERMITE GUARDAR LA ORDEN
    List<Orden> findAll();
    String generarNumeroOrden();

}
