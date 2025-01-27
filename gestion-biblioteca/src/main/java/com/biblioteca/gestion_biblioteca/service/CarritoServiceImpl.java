// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Carrito; // Importa la clase Carrito para gestionar los datos del carrito de compras.
import com.biblioteca.gestion_biblioteca.model.Libro; // Importa la clase Libro para manejar los datos de los libros.

import java.util.Map; // Importa Map para manejar las colecciones de libros y sus cantidades.
import java.util.Optional; // Importa Optional para manejar valores que pueden ser nulos.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Anotación @Service para declarar esta clase como un componente de servicio de Spring.
@Service
public class CarritoServiceImpl implements CarritoService {

    // Inyección de dependencias para los servicios de Libro y Stock.
    @Autowired
    private final LibroService libroService;
    private final StockService stockService;

    // Constructor para inicializar los servicios necesarios.
    @Autowired
    public CarritoServiceImpl(LibroService libroService, StockService stockService) {
        this.libroService = libroService;
        this.stockService = stockService;
    }

    // Método para ver el contenido del carrito.
    @Override
    public Map<String, Integer> verCarrito(Carrito carrito) {
        return carrito.obtenerLibrosIsbn(); // Devuelve un mapa de ISBN y cantidades de libros en el carrito.
    }

    // Método para agregar un libro al carrito.
    @Override
    public void agregarLibroAlCarrito(Carrito carrito, String isbn, Integer cantidad) {
        Optional<Libro> libroExistente = libroService.buscarPorIsbn(isbn);

        if (libroExistente.isPresent()) {
            Optional<Libro> libroExistenteCarro = carrito.contieneLibroPorIsbn(isbn);

            if (libroExistenteCarro.isPresent()) {
                actualizarCantidadLibroDelCarrito(carrito, isbn, cantidad); // Actualiza la cantidad si el libro ya está en el carrito.
            } else {
                Libro libro = libroExistente.get();
                if (stockService.suficienteStock(libro, cantidad, 0)) {
                    carrito.agregarLibro(libro, cantidad); // Agrega el libro al carrito.
                    stockService.reducirCantidad(isbn, cantidad); // Reduce el stock disponible.
                } else {
                    throw new IllegalArgumentException("No hay suficiente stock del libro con ISBN " + isbn);
                }
            }
        } else {
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " no existe.");
        }
    }

    // Método para quitar un libro del carrito.
    @Override
    public void quitarLibroDelCarrito(Carrito carrito, String isbn) {
        Optional<Libro> libroExistente = carrito.contieneLibroPorIsbn(isbn);

        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            stockService.aumentarCantidad(isbn, obtenerCantidadDeLibro(carrito, libro.getIsbn())); // Devuelve la cantidad al stock.
            carrito.quitarLibro(libro); // Quita el libro del carrito.
        } else {
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " no se encuentra en el carrito.");
        }
    }

    // Método para actualizar la cantidad de un libro en el carrito.
    @Override
    public void actualizarCantidadLibroDelCarrito(Carrito carrito, String isbn, Integer cantidad) {
        Optional<Libro> libroExistente = carrito.contieneLibroPorIsbn(isbn);

        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            Integer cantidadActual = obtenerCantidadDeLibro(carrito, libro.getIsbn());

            if (!stockService.suficienteStock(libro, cantidad, cantidadActual)) {
                throw new IllegalArgumentException("No hay suficiente stock del libro con ISBN " + isbn);
            }
            if (cantidadActual < cantidad) {
                stockService.reducirCantidad(libro.getIsbn(), cantidad - cantidadActual);
            } else {
                stockService.aumentarCantidad(libro.getIsbn(), cantidadActual - cantidad);
            }
            carrito.actualizarCantidad(libro, cantidad); // Actualiza la cantidad en el carrito.
        } else {
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " no se encuentra en el carrito.");
        }
    }

    // Método para devolver los libros al stock.
    @Override
    public void devolverLibrosAlStock(Carrito carrito) {
        for (Map.Entry<Libro, Integer> entry : carrito.obtenerLibros().entrySet()) {
            Libro libro = entry.getKey();
            Integer cantidadEnCarrito = entry.getValue();
            stockService.aumentarCantidad(libro.getIsbn(), cantidadEnCarrito); // Devuelve al stock las cantidades de cada libro.
        }
    }

    // Método para obtener la cantidad de un libro específico en el carrito.
    @Override
    public Integer obtenerCantidadDeLibro(Carrito carrito, String isbn) {
        for (Map.Entry<Libro, Integer> entry : carrito.obtenerLibros().entrySet()) {
            Libro libro = entry.getKey();
            if (libro.getIsbn().equals(isbn)) {
                return entry.getValue(); // Devuelve la cantidad del libro con el ISBN especificado.
            }
        }
        return 0; // Si no se encuentra el libro, devuelve 0.
    }

    // Método para vaciar completamente el carrito.
    @Override
    public void vaciarCarrito(Carrito carrito) {
        carrito.vaciarCarrito(); // Elimina todos los libros y cantidades del carrito.
    }
}
