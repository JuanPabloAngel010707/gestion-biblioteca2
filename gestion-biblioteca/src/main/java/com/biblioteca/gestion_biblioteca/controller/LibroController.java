// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.controller;

// Importa las clases necesarias para trabajar con los libros y servicios asociados.
import com.biblioteca.gestion_biblioteca.model.Libro; // Importa la clase Libro.
import com.biblioteca.gestion_biblioteca.service.LibroService; // Importa el servicio de libros.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importa los códigos de estado HTTP.
import org.springframework.http.ResponseEntity; // Importa la clase para encapsular respuestas HTTP.
import org.springframework.web.bind.annotation.*; // Importa las anotaciones necesarias para trabajar con REST.
import jakarta.validation.Valid; // Importa la anotación para validar objetos.

import java.util.List; // Importa las colecciones para manejar listas.
import java.util.Optional; // Importa Optional para manejar valores nulos.

@RestController // Indica que esta clase es un controlador de una API REST.
@RequestMapping("/api/libros") // Define la ruta base para las peticiones a este controlador.
public class LibroController {

    private final LibroService libroService; // Declara una dependencia del servicio LibroService.

    // Constructor con inyección de dependencias para inicializar el servicio de libros.
    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // Método para obtener todos los libros de la base de datos.
    @GetMapping // Mapea las peticiones GET a esta ruta.
    public ResponseEntity<List<Libro>> obtenerTodos() {
        // Llama al servicio para obtener la lista de libros.
        List<Libro> libros = libroService.buscarTodos();
        // Devuelve la lista de libros con un código de estado 200 (OK).
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    // Método para obtener un libro específico por su ISBN.
    @GetMapping("/{isbn}") // Mapea las peticiones GET a la ruta "/{isbn}".
    public ResponseEntity<Libro> obtenerPorIsbn(@PathVariable String isbn) {
        // Busca el libro en el servicio por su ISBN.
        Optional<Libro> libro = libroService.buscarPorIsbn(isbn);
        // Si se encuentra el libro, lo devuelve con un código HTTP 200 (OK), si no lo encuentra, devuelve un código HTTP 404 (Not Found).
        return libro.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Método para buscar libros por el nombre del autor.
    @GetMapping("/autor/{autorNombre}") // Mapea las peticiones GET a la ruta "/autor/{autorNombre}".
    public ResponseEntity<List<Libro>> buscarPorAutor(@PathVariable String autorNombre) {
        // Busca los libros del autor en el servicio.
        List<Libro> libros = libroService.buscarPorAutor(autorNombre);
        // Si no hay libros, devuelve un código HTTP 404 (Not Found).
        if (libros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Si hay libros, los devuelve con un código HTTP 200 (OK).
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    // Método para crear un nuevo libro.
    @PostMapping("/crear") // Mapea las peticiones POST a la ruta "/crear".
    public ResponseEntity<?> crearLibro(@Valid @RequestBody Libro libro, @RequestParam String nombreAutor) {
        try {
            // Llama al servicio para crear el libro con el autor proporcionado.
            Libro nuevoLibro = libroService.crearLibro(libro, nombreAutor);
            // Devuelve el libro creado con un código de estado HTTP 201 (Created).
            return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, si el libro ya existe), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para eliminar un libro por su ISBN.
    @DeleteMapping("/{isbn}") // Mapea las peticiones DELETE a la ruta "/{isbn}".
    public ResponseEntity<?> eliminarLibro(@PathVariable String isbn) {
        try {
            // Llama al servicio para eliminar el libro.
            libroService.eliminarLibro(isbn);
            // Si la eliminación es exitosa, devuelve un código HTTP 204 (No Content).
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, si el libro no existe), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para buscar libros por su título.
    @GetMapping("/buscar/{titulo}") // Mapea las peticiones GET a la ruta "/buscar/{titulo}".
    public ResponseEntity<List<Libro>> buscarPorTitulo(@PathVariable String titulo) {
        // Busca los libros por título en el servicio.
        List<Libro> libros = libroService.buscarPorTitulo(titulo);
        // Si no se encuentran libros, devuelve un código HTTP 404 (Not Found).
        if (libros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Si se encuentran libros, los devuelve con un código HTTP 200 (OK).
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    // Método para editar un libro existente por su ISBN.
    @PutMapping("/{isbn}") // Mapea las peticiones PUT a la ruta "/{isbn}".
    public ResponseEntity<?> editarLibro(@Valid @PathVariable String isbn, @RequestBody Libro libroActualizado) {
        try {
            // Llama al servicio para actualizar el libro con el ISBN proporcionado.
            Libro libroEditado = libroService.actualizarLibro(isbn, libroActualizado);
            // Devuelve el libro editado con un código HTTP 200 (OK).
            return new ResponseEntity<>(libroEditado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, si el libro no existe), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
