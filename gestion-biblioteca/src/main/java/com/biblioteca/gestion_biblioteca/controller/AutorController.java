// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.controller;

// Importa las clases necesarias para trabajar con los autores y servicios asociados.
import com.biblioteca.gestion_biblioteca.model.Autor; // Importa la clase Autor.
import com.biblioteca.gestion_biblioteca.service.AutorService; // Importa el servicio de autores.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importa los códigos de estado HTTP.
import org.springframework.http.ResponseEntity; // Importa la clase para encapsular respuestas HTTP.
import jakarta.validation.Valid; // Importa la anotación para validar objetos.
import org.springframework.web.bind.annotation.*; // Importa las anotaciones necesarias para trabajar con REST.

import java.util.List; // Importa las colecciones para manejar listas.
import java.util.Optional; // Importa Optional para manejar valores nulos.

@RestController // Indica que esta clase es un controlador de una API REST.
@RequestMapping("/api/autores") // Define la ruta base para las peticiones a este controlador.
public class AutorController {

    private final AutorService autorService; // Declara una dependencia del servicio AutorService.

    // Constructor con inyección de dependencias para inicializar el servicio de autores.
    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // Método para obtener todos los autores de la base de datos.
    @GetMapping // Mapea las peticiones GET a esta ruta.
    public ResponseEntity<List<Autor>> obtenerTodos() {
        // Llama al servicio para obtener la lista de autores.
        List<Autor> autores = autorService.buscarTodos();
        // Devuelve la lista de autores con un código de estado 200 (OK).
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    // Método para obtener un autor específico por su ID.
    @GetMapping("/{id}") // Mapea las peticiones GET a la ruta "/{id}".
    public ResponseEntity<Autor> obtenerPorId(@PathVariable Long id) {
        // Busca el autor en el servicio por su ID.
        Optional<Autor> autor = autorService.buscarPorId(id);
        // Si se encuentra el autor, lo devuelve con un código de estado 200 (OK), si no, 404 (Not Found).
        return autor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Método para crear un nuevo autor.
    @PostMapping // Mapea las peticiones POST a esta ruta.
    public ResponseEntity<?> crearAutor(@Valid @RequestBody Autor autor) {
        // Llama al servicio para crear el autor y lo guarda.
        Autor nuevoAutor = autorService.crearAutor(autor);
        // Devuelve el autor creado con un código de estado 201 (Created).
        return new ResponseEntity<>(nuevoAutor, HttpStatus.CREATED);
    }

    // Método para eliminar un autor dado su ID.
    @DeleteMapping("/{id}") // Mapea las peticiones DELETE a la ruta "/{id}".
    public ResponseEntity<?> eliminarAutor(@PathVariable Long id) {
        try {
            // Llama al servicio para eliminar el autor.
            autorService.eliminarAutor(id);
            // Si la eliminación es exitosa, devuelve un código de estado 204 (No Content).
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, el autor no existe), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
