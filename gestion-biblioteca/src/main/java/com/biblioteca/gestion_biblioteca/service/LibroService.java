// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importa las clases necesarias para manejar listas y valores nulos.
import java.util.List;
import java.util.Optional; // Importa Optional para manejar valores que pueden ser nulos.
import com.biblioteca.gestion_biblioteca.model.Libro; // Importa la clase Libro para trabajar con libros dentro de los métodos.

// Interfaz que define las operaciones que se pueden realizar sobre los libros.
// Estas operaciones serán implementadas por una clase de servicio.
public interface LibroService {

    // Método para buscar todos los libros en la base de datos.
    List<Libro> buscarTodos();

    // Método para buscar libros por título. Permite buscar libros cuyo título contenga el texto proporcionado.
    List<Libro> buscarPorTitulo(String titulo);

    // Método para buscar libros por el nombre de su autor. Permite buscar libros escritos por un autor específico.
    List<Libro> buscarPorAutor(String autorNombre);

    // Método para buscar un libro por su ISBN. Devuelve un Optional para manejar el caso en que el libro no se encuentre.
    Optional<Libro> buscarPorIsbn(String isbn);

    // Método para crear un libro y asociarlo con un autor. Recibe un objeto Libro y el nombre del autor.
    Libro crearLibro(Libro libro, String nombreAutor);

    // Método para eliminar un libro utilizando su ISBN.
    void eliminarLibro(String isbn);

    // Método para actualizar los detalles de un libro existente. Recibe un ISBN y un objeto Libro con los nuevos datos.
    Libro actualizarLibro(String isbn, Libro libro);

    // Método para verificar si un libro con el ISBN proporcionado ya existe en la base de datos.
    Boolean libroExistente(String isbn);
}
