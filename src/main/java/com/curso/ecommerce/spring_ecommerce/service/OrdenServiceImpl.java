package com.curso.ecommerce.spring_ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.spring_ecommerce.model.Orden;
import com.curso.ecommerce.spring_ecommerce.repository.IOrdenRepository;

@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    @Override
    public String generarNumeroOrden() {
        List<Orden> ordenes = findAll();
    
        // Si no hay órdenes, la primera será 1, de lo contrario, obtenemos el número máximo
        int numero = ordenes.isEmpty() 
            ? 1 
            : ordenes.stream()
                      .mapToInt(o -> Integer.parseInt(o.getNumero()))
                      .max()
                      .orElse(0) + 1;  // Si no hay máximo, devolvemos 0, lo que garantizará que el número comience desde 1
    
        // Usamos String.format() para formatear el número con ceros a la izquierda
        return String.format("%010d", numero);  // Esto genera un número de 10 dígitos con ceros a la izquierda
    }

    

    // public String generarNumeroOrden() {
    //     int numero = 0;
    //     String numeroConcatenado = "";
    //     List<Orden> ordenes = findAll();
    //     List<Integer> numeros = new ArrayList<Integer>();

    //     ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));

    //     if (ordenes.isEmpty()) {
    //         numero = 1;
    //     } else {
    //         numero = numeros.stream().max(Integer::compare).get();
    //         numero++;
    //     }

    //     if (numero < 10) {
    //         numeroConcatenado = "000000000" + String.valueOf(numero);
    //     } else if (numero < 100) {
    //         numeroConcatenado = "00000000" + String.valueOf(numero);
    //     } else if (numero < 100) {
    //         numeroConcatenado = "0000000" + String.valueOf(numero);
    //     } else if (numero < 1000) {
    //         numeroConcatenado = "000000" + String.valueOf(numero);
    //     } else if (numero < 10000) {
    //         numeroConcatenado = "00000" + String.valueOf(numero);
    //     }

    //     return numeroConcatenado;
    // }

}
