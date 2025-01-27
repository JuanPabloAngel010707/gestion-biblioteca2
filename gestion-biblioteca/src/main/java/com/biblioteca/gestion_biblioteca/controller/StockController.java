// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.controller;

// Importa las clases necesarias para el funcionamiento del controlador.
import org.springframework.beans.factory.annotation.Autowired; // Importa la anotación para inyección de dependencias.
import com.biblioteca.gestion_biblioteca.service.StockService; // Importa el servicio de stock.
import org.springframework.http.ResponseEntity; // Importa la clase para encapsular respuestas HTTP.
import org.springframework.web.bind.annotation.*; // Importa las anotaciones necesarias para trabajar con REST.

@RestController // Indica que esta clase es un controlador de una API REST.
@RequestMapping("/api/stock") // Define la ruta base para las peticiones a este controlador.
public class StockController {

    private final StockService stockService; // Declara una dependencia del servicio StockService.

    // Constructor con inyección de dependencias para inicializar el servicio de stock.
    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Método para reducir la cantidad de un libro en el stock.
    @PutMapping("/reducir/{isbn}") // Mapea las peticiones PUT a la ruta "/reducir/{isbn}".
    public ResponseEntity<String> reducirCantidad(@PathVariable String isbn, @RequestParam Integer cantidad) {
        try {
            // Llama al servicio para reducir la cantidad de libros en el stock.
            stockService.reducirCantidad(isbn, cantidad);
            // Devuelve una respuesta con un mensaje de éxito y un código HTTP 200 (OK).
            return ResponseEntity.ok("Cantidad reducida correctamente.");
        } catch (IllegalArgumentException e) {
            // Si ocurre un error, devuelve un mensaje con un código HTTP 400 (Bad Request).
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Método para aumentar la cantidad de un libro en el stock.
    @PutMapping("/aumentar/{isbn}") // Mapea las peticiones PUT a la ruta "/aumentar/{isbn}".
    public ResponseEntity<String> aumentarCantidad(@PathVariable String isbn, @RequestParam Integer cantidad) {
        try {
            // Llama al servicio para aumentar la cantidad de libros en el stock.
            stockService.aumentarCantidad(isbn, cantidad);
            // Devuelve una respuesta con un mensaje de éxito y un código HTTP 200 (OK).
            return ResponseEntity.ok("Cantidad aumentada correctamente.");
        } catch (IllegalArgumentException e) {
            // Si ocurre un error, devuelve un mensaje con un código HTTP 400 (Bad Request).
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
