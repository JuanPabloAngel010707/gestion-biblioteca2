// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importa las clases necesarias para la gestión del stock y los libros.
import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.repository.LibroRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Clase que implementa la lógica de la interfaz StockService para gestionar el stock de libros.
@Service
public class StockServiceImpl implements StockService {

    // Repositorio para interactuar con la base de datos de libros.
    @Autowired
    private final LibroRepository libroRepository;

    // Servicio adicional para gestionar libros.
    private final LibroService libroService;

    // Constructor para inyectar las dependencias necesarias.
    @Autowired
    public StockServiceImpl(LibroRepository libroRepository, LibroService libroService) {
        this.libroRepository = libroRepository;
        this.libroService = libroService;
    }

    // Método para reducir la cantidad de ejemplares disponibles de un libro en el stock.
    // Si la cantidad resultante es menor que 0, establece la cantidad del libro en 0.
    @Override
    public void reducirCantidad(String isbn, Integer cantidad) {
        Optional<Libro> libroExistente = libroService.buscarPorIsbn(isbn);

        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            if (libro.getCantidad() - cantidad < 0) {
                libro.setCantidad(0); // Evita cantidades negativas.
            } else {
                libro.setCantidad(libro.getCantidad() - cantidad);
            }
            libroRepository.save(libro); // Guarda los cambios en la base de datos.
        } else {
            // Lanza una excepción si el libro no existe en la base de datos.
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " no existe.");
        }
    }

    // Método para aumentar la cantidad de ejemplares disponibles de un libro en el stock.
    @Override
    public void aumentarCantidad(String isbn, Integer cantidad) {
        Optional<Libro> libroExistente = libroService.buscarPorIsbn(isbn);

        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            libro.setCantidad(libro.getCantidad() + cantidad); // Aumenta la cantidad disponible.
            libroRepository.save(libro); // Guarda los cambios en la base de datos.
        } else {
            // Lanza una excepción si el libro no existe en la base de datos.
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " no existe.");
        }
    }

    // Método para verificar si hay suficiente stock de un libro para cubrir la cantidad solicitada,
    // considerando los libros que el usuario ya tiene en su carrito.
    @Override
    public Boolean suficienteStock(Libro libro, Integer cantidad, Integer cantidadCarrito) {
        if (libro.getCantidad() + cantidadCarrito >= cantidad) {
            return true; // Hay suficiente stock.
        } else {
            return false; // No hay suficiente stock.
        }
    }
}
