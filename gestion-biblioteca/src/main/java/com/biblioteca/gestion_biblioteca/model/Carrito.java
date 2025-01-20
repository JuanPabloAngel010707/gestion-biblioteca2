package com.biblioteca.gestion_biblioteca.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Carrito {
    
    private Map<Libro, Integer> libros;

    public Carrito() {
        this.libros = new HashMap<>();
    }
    
    
    public void agregarLibro(Libro libro, Integer cantidad) {
        if (libros.containsKey(libro)) {

            libros.put(libro, libros.get(libro) + cantidad);
        } else {

            libros.put(libro, cantidad);
        }
    }

    public void quitarLibro(Libro libro) {
        libros.remove(libro);
    }


    public void actualizarCantidad(Libro libro, Integer cantidad) {
        if (libros.containsKey(libro)) {
            if (cantidad <= 0) {
                libros.remove(libro); 
            } else {
                libros.put(libro, cantidad);
            }
        }
    }

    public Optional<Libro> contieneLibroPorIsbn(String isbn) {
        for (Libro libro : libros.keySet()) {
            if (libro.getIsbn().equals(isbn)) {
            	return Optional.of(libro); 
            }
        }
        return Optional.empty(); 
    }
    
    public Map<String, Integer> obtenerLibrosIsbn() {
        Map<String, Integer> librosConIsbn = new HashMap<>();
        for (Map.Entry<Libro, Integer> entry : libros.entrySet()) {
            librosConIsbn.put(entry.getKey().getIsbn(), entry.getValue());
        }
        return librosConIsbn;
    }
    
    public Map<Libro, Integer> obtenerLibros() {
        return libros;
    }
    
    public void vaciarCarrito() {
        libros.clear();  
    }

}

