// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importa las clases necesarias para la gestión del stock.
import com.biblioteca.gestion_biblioteca.model.Libro;

// Interfaz que define las operaciones relacionadas con el stock de libros.
// Estas operaciones serán implementadas por una clase de servicio.
public interface StockService {

    // Método para reducir la cantidad de ejemplares disponibles de un libro en el stock.
    // Recibe el ISBN del libro y la cantidad a reducir.
    void reducirCantidad(String isbn, Integer cantidad);

    // Método para aumentar la cantidad de ejemplares disponibles de un libro en el stock.
    // Recibe el ISBN del libro y la cantidad a aumentar.
    void aumentarCantidad(String isbn, Integer cantidad);

    // Método para verificar si hay suficiente stock de un libro para una operación determinada.
    // Recibe el libro, la cantidad requerida y la cantidad de ese libro que ya está en el carrito del usuario.
    // Devuelve true si hay suficiente stock, false en caso contrario.
    Boolean suficienteStock(Libro libro, Integer cantidad, Integer cantidadCarrito);
}
