// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.model;

import java.util.HashMap; // Importa la clase HashMap para manejar el almacenamiento de libros.
import java.util.Map; // Importa la interfaz Map para definir colecciones clave-valor.
import java.util.Optional; // Importa Optional para manejar valores nulos de manera segura.

// Clase que representa un carrito de compras.
public class Carrito {

    // Mapa que almacena los libros y sus cantidades asociadas.
    private Map<Libro, Integer> libros;

    // Constructor que inicializa el mapa de libros.
    public Carrito() {
        this.libros = new HashMap<>();
    }

    // Método para agregar un libro al carrito.
    public void agregarLibro(Libro libro, Integer cantidad) {
        // Si el libro ya está en el carrito, se incrementa su cantidad.
        if (libros.containsKey(libro)) {
            libros.put(libro, libros.get(libro) + cantidad);
        } else {
            // Si el libro no está en el carrito, se agrega con la cantidad indicada.
            libros.put(libro, cantidad);
        }
    }

    // Método para quitar un libro del carrito.
    public void quitarLibro(Libro libro) {
        libros.remove(libro);
    }

    // Método para actualizar la cantidad de un libro en el carrito.
    public void actualizarCantidad(Libro libro, Integer cantidad) {
        // Si el libro está en el carrito.
        if (libros.containsKey(libro)) {
            // Si la nueva cantidad es menor o igual a 0, se elimina el libro del carrito.
            if (cantidad <= 0) {
                libros.remove(libro);
            } else {
                // Si la cantidad es mayor a 0, se actualiza con la nueva cantidad.
                libros.put(libro, cantidad);
            }
        }
    }

    // Método para verificar si un libro con un ISBN específico está en el carrito.
    public Optional<Libro> contieneLibroPorIsbn(String isbn) {
        for (Libro libro : libros.keySet()) {
            // Si el ISBN coincide, devuelve el libro envuelto en un Optional.
            if (libro.getIsbn().equals(isbn)) {
                return Optional.of(libro);
            }
        }
        // Si no se encuentra el libro, devuelve un Optional vacío.
        return Optional.empty();
    }

    // Método para obtener un mapa de los ISBN de los libros en el carrito con sus cantidades.
    public Map<String, Integer> obtenerLibrosIsbn() {
        Map<String, Integer> librosConIsbn = new HashMap<>();
        // Recorre el mapa y extrae el ISBN y la cantidad de cada libro.
        for (Map.Entry<Libro, Integer> entry : libros.entrySet()) {
            librosConIsbn.put(entry.getKey().getIsbn(), entry.getValue());
        }
        return librosConIsbn;
    }

    // Método para obtener el mapa completo de libros en el carrito.
    public Map<Libro, Integer> obtenerLibros() {
        return libros;
    }

    // Método para vaciar el carrito eliminando todos los libros.
    public void vaciarCarrito() {
        libros.clear();
    }
}
