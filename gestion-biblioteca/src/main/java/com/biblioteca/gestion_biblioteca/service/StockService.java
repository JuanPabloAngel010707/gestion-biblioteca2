package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Libro;

public interface StockService {

    void reducirCantidad(String isbn, Integer cantidad);
    void aumentarCantidad(String isbn, Integer cantidad);
    Boolean suficienteStock(Libro libro, Integer cantidad, Integer cantidadCarrito);

}
