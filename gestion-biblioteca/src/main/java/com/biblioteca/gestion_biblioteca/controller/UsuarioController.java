// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.controller;

// Importa las clases necesarias para trabajar con los usuarios y los servicios asociados.
import com.biblioteca.gestion_biblioteca.model.Usuario; // Importa la clase Usuario.
import com.biblioteca.gestion_biblioteca.service.UsuarioService; // Importa el servicio de usuarios.

import jakarta.validation.Valid; // Importa la validación de datos con @Valid.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importa los códigos de estado HTTP.
import org.springframework.http.ResponseEntity; // Importa la clase para encapsular respuestas HTTP.
import org.springframework.web.bind.annotation.*; // Importa las anotaciones necesarias para trabajar con REST.

import java.util.List; // Importa las colecciones para manejar listas.
import java.util.Optional; // Importa Optional para manejar valores nulos.

@RestController // Indica que esta clase es un controlador de una API REST.
@RequestMapping("/api/usuarios") // Define la ruta base para las peticiones a este controlador.
public class UsuarioController {

    private final UsuarioService usuarioService; // Declara una dependencia del servicio UsuarioService.

    // Constructor con inyección de dependencias para inicializar el servicio de usuarios.
    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Método para obtener todos los usuarios de la base de datos.
    @GetMapping // Mapea las peticiones GET a esta ruta.
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        // Llama al servicio para obtener la lista de usuarios.
        List<Usuario> usuarios = usuarioService.buscarTodos();
        // Devuelve la lista de usuarios con un código de estado 200 (OK).
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Método para obtener un usuario específico por su DNI.
    @GetMapping("/{dni}") // Mapea las peticiones GET a la ruta "/{dni}".
    public ResponseEntity<Usuario> obtenerPorDni(@PathVariable String dni) {
        // Busca el usuario en el servicio por su DNI.
        Optional<Usuario> usuario = usuarioService.buscarPorDni(dni);
        // Si se encuentra el usuario, lo devuelve con un código HTTP 200 (OK), si no lo encuentra, devuelve un código HTTP 404 (Not Found).
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Método para crear un nuevo usuario.
    @PostMapping("/crear") // Mapea las peticiones POST a la ruta "/crear".
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            // Llama al servicio para crear el nuevo usuario.
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            // Devuelve el usuario creado con un código HTTP 201 (Created).
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, si los datos son inválidos), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para eliminar un usuario por su DNI.
    @DeleteMapping("/{dni}") // Mapea las peticiones DELETE a la ruta "/{dni}".
    public ResponseEntity<?> eliminarUsuario(@PathVariable String dni) {
        try {
            // Llama al servicio para eliminar el usuario con el DNI proporcionado.
            usuarioService.eliminarUsuario(dni);
            // Si la eliminación es exitosa, devuelve un código HTTP 204 (No Content).
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, si el usuario no existe), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para editar un usuario existente.
    @PutMapping("/{dni}") // Mapea las peticiones PUT a la ruta "/{dni}".
    public ResponseEntity<?> editarUsuario(@Valid @PathVariable String dni, @RequestBody Usuario usuarioActualizado) {
        try {
            // Llama al servicio para actualizar el usuario con el DNI proporcionado.
            Usuario usuarioEditado = usuarioService.actualizarUsuario(dni, usuarioActualizado);
            // Devuelve el usuario editado con un código HTTP 200 (OK).
            return new ResponseEntity<>(usuarioEditado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, si el usuario no existe), devuelve un código 400 (Bad Request) con el mensaje de error.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
