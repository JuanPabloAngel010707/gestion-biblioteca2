package com.biblioteca.gestion_biblioteca.controller;

import com.biblioteca.gestion_biblioteca.model.Carrito;
import com.biblioteca.gestion_biblioteca.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;
    
    private static final String SESSION_CARRITO_KEY = "carrito";
    
    private Carrito obtenerCarritoDeSesion(HttpSession session) {
        Carrito carrito = (Carrito) session.getAttribute(SESSION_CARRITO_KEY);
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute(SESSION_CARRITO_KEY, carrito);
        }
        return carrito;
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Integer>> verCarrito(HttpSession session) {
        Carrito carrito = obtenerCarritoDeSesion(session);
        Map<String, Integer> librosEnCarrito = carritoService.verCarrito(carrito);
        return ResponseEntity.ok(librosEnCarrito);
    }

    @PostMapping("/agregar/{isbn}/{cantidad}")
    public ResponseEntity<String> agregarLibro(@PathVariable String isbn, @PathVariable Integer cantidad, HttpSession session) {
    	Carrito carrito = obtenerCarritoDeSesion(session);
    	try {
            carritoService.agregarLibroAlCarrito(carrito, isbn, cantidad);
            return ResponseEntity.ok("Libro agregado exitosamente al carrito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/quitar/{isbn}")
    public ResponseEntity<String> quitarLibroDelCarrito(@PathVariable String isbn, HttpSession session) {
        Carrito carrito = obtenerCarritoDeSesion(session);
        try {
            carritoService.quitarLibroDelCarrito(carrito, isbn);
            return ResponseEntity.ok("Libro quitado exitosamente del carrito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{isbn}/{cantidad}")
    public ResponseEntity<String> actualizarCantidadLibroDelCarrito(@PathVariable String isbn, @PathVariable Integer cantidad, HttpSession session) {
    	Carrito carrito = obtenerCarritoDeSesion(session);
    	try {
            carritoService.actualizarCantidadLibroDelCarrito(carrito, isbn, cantidad);
            return ResponseEntity.ok("Cantidad del libro actualizada exitosamente en el carrito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/cerrar-sesion")
    public ResponseEntity<String> cerrarSesion(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Sesión destruida correctamente.");
    }
}

