// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Carrito; // Importa la clase Carrito para manejar los datos relacionados con el carrito de compras.
import java.util.Map; // Importa Map para manejar las colecciones de libros y sus cantidades.

// Interfaz que define las operaciones que se pueden realizar sobre el carrito de compras.
// Estas operaciones serán implementadas por una clase de servicio.
public interface CarritoService {

    // Método para ver el contenido del carrito.
    // Devuelve un mapa donde las claves son los ISBN de los libros y los valores son las cantidades correspondientes.
    Map<String, Integer> verCarrito(Carrito carrito);

    // Método para agregar un libro al carrito.
    // Recibe el carrito, el ISBN del libro y la cantidad a agregar.
    void agregarLibroAlCarrito(Carrito carrito, String isbn, Integer cantidad);

    // Método para quitar un libro del carrito.
    // Recibe el carrito y el ISBN del libro a eliminar.
    void quitarLibroDelCarrito(Carrito carrito, String isbn);

    // Método para actualizar la cantidad de un libro en el carrito.
    // Recibe el carrito, el ISBN del libro y la nueva cantidad.
    void actualizarCantidadLibroDelCarrito(Carrito carrito, String isbn, Integer cantidad);

    // Método para devolver al stock los libros contenidos en el carrito.
    // Recibe el carrito y ajusta las cantidades en el inventario.
    void devolverLibrosAlStock(Carrito carrito);

    // Método para vaciar completamente el carrito.
    // Elimina todos los libros y sus cantidades del carrito.
    void vaciarCarrito(Carrito carrito);

    // Método para obtener la cantidad de un libro específico en el carrito.
    // Recibe el carrito y el ISBN del libro, y devuelve la cantidad asociada.
    Integer obtenerCantidadDeLibro(Carrito carrito, String isbn);
}
