// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.controller;

// Importa las clases necesarias para trabajar con el carrito y su lógica de negocio.
import com.biblioteca.gestion_biblioteca.model.Carrito; // Modelo que representa un carrito de compras.
import com.biblioteca.gestion_biblioteca.service.CarritoService; // Servicio que contiene la lógica para gestionar el carrito.
import org.springframework.beans.factory.annotation.Autowired; // Anotación para la inyección de dependencias.
import org.springframework.http.ResponseEntity; // Clase utilizada para encapsular respuestas HTTP.
import org.springframework.stereotype.Controller; // Marca esta clase como un controlador en Spring.
import org.springframework.web.bind.annotation.*; // Importa anotaciones para manejar solicitudes HTTP.

import java.util.Map; // Clase para manejar colecciones clave-valor.
import jakarta.servlet.http.HttpSession; // Clase para manejar sesiones HTTP.

@Controller // Marca esta clase como un controlador de Spring MVC.
@RequestMapping("/api/carrito") // Define la ruta base para todas las solicitudes relacionadas con el carrito.
public class CarritoController {

    @Autowired
    private CarritoService carritoService; // Servicio para gestionar la lógica del carrito.

    private static final String SESSION_CARRITO_KEY = "carrito"; // Clave para almacenar el carrito en la sesión.

    // Obtiene el carrito de la sesión. Si no existe, crea uno nuevo y lo guarda en la sesión.
    private Carrito obtenerCarritoDeSesion(HttpSession session) {
        Carrito carrito = (Carrito) session.getAttribute(SESSION_CARRITO_KEY); // Intenta obtener el carrito de la sesión.
        if (carrito == null) { // Si no existe un carrito.
            carrito = new Carrito(); // Crea uno nuevo.
            session.setAttribute(SESSION_CARRITO_KEY, carrito); // Lo guarda en la sesión.
        }
        return carrito; // Devuelve el carrito obtenido o creado.
    }

    // Devuelve el contenido del carrito almacenado en la sesión.
    @GetMapping
    public ResponseEntity<Map<String, Integer>> verCarrito(HttpSession session) {
        Carrito carrito = obtenerCarritoDeSesion(session); // Obtiene el carrito de la sesión.
        Map<String, Integer> librosEnCarrito = carritoService.verCarrito(carrito); // Obtiene los libros en el carrito usando el servicio.
        return ResponseEntity.ok(librosEnCarrito); // Devuelve los libros con un código de estado 200 (OK).
    }

    // Agrega un libro al carrito con la cantidad indicada.
    @PostMapping("/agregar/{isbn}/{cantidad}")
    public ResponseEntity<String> agregarLibro(@PathVariable String isbn, @PathVariable Integer cantidad, HttpSession session) {
        Carrito carrito = obtenerCarritoDeSesion(session); // Obtiene el carrito de la sesión.
        try {
            carritoService.agregarLibroAlCarrito(carrito, isbn, cantidad); // Intenta agregar el libro al carrito usando el servicio.
            return ResponseEntity.ok("Libro agregado exitosamente al carrito."); // Devuelve un mensaje de éxito con estado 200 (OK).
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Si ocurre un error, devuelve el mensaje con estado 400 (Bad Request).
        }
    }

    // Elimina un libro del carrito usando su ISBN.
    @DeleteMapping("/quitar/{isbn}")
    public ResponseEntity<String> quitarLibroDelCarrito(@PathVariable String isbn, HttpSession session) {
        Carrito carrito = obtenerCarritoDeSesion(session); // Obtiene el carrito de la sesión.
        try {
            carritoService.quitarLibroDelCarrito(carrito, isbn); // Intenta quitar el libro del carrito usando el servicio.
            return ResponseEntity.ok("Libro quitado exitosamente del carrito."); // Devuelve un mensaje de éxito con estado 200 (OK).
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Si ocurre un error, devuelve el mensaje con estado 400 (Bad Request).
        }
    }

    // Actualiza la cantidad de un libro en el carrito.
    @PutMapping("/actualizar/{isbn}/{cantidad}")
    public ResponseEntity<String> actualizarCantidadLibroDelCarrito(@PathVariable String isbn, @PathVariable Integer cantidad, HttpSession session) {
        Carrito carrito = obtenerCarritoDeSesion(session); // Obtiene el carrito de la sesión.
        try {
            carritoService.actualizarCantidadLibroDelCarrito(carrito, isbn, cantidad); // Intenta actualizar la cantidad del libro usando el servicio.
            return ResponseEntity.ok("Cantidad del libro actualizada exitosamente en el carrito."); // Devuelve un mensaje de éxito con estado 200 (OK).
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Si ocurre un error, devuelve el mensaje con estado 400 (Bad Request).
        }
    }

    // Cierra la sesión actual y elimina los datos almacenados.
    @PostMapping("/cerrar-sesion")
    public ResponseEntity<String> cerrarSesion(HttpSession session) {
        session.invalidate(); // Invalida la sesión, eliminando los datos asociados.
        return ResponseEntity.ok("Sesión destruida correctamente."); // Devuelve un mensaje de éxito con estado 200 (OK).
    }
}
