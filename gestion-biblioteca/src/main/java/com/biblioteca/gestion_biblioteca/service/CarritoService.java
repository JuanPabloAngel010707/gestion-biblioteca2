package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Carrito;

import java.util.Map;

public interface CarritoService {

	Map<String, Integer> verCarrito(Carrito carrito);

    void agregarLibroAlCarrito(Carrito carrito, String isbn, Integer cantidad);

    void quitarLibroDelCarrito(Carrito carrito, String isbn);

    void actualizarCantidadLibroDelCarrito(Carrito carrito, String isbn, Integer cantidad);
    
    void devolverLibrosAlStock(Carrito carrito);
    
    void vaciarCarrito(Carrito carrito);
    
    Integer obtenerCantidadDeLibro(Carrito carrito, String isbn);
}

