// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.listener;

// Importa las clases necesarias para trabajar con el carrito y los servicios asociados.
import com.biblioteca.gestion_biblioteca.model.Carrito; // Importa la clase Carrito.
import com.biblioteca.gestion_biblioteca.service.CarritoService; // Importa el servicio del carrito.
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession; // Importa la clase para manejar sesiones HTTP.
import jakarta.servlet.http.HttpSessionEvent; // Importa la clase para eventos de sesión.
import jakarta.servlet.http.HttpSessionListener; // Importa la interfaz para escuchar eventos de sesión.

// Clase que implementa HttpSessionListener para manejar eventos de sesión.
public class SessionListener implements HttpSessionListener {

    @Autowired // Inyección de dependencia para el servicio de carrito.
    private CarritoService carritoService;

    // Constructor para inicializar el servicio de carrito.
    public SessionListener(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // Método que se ejecuta cuando se crea una sesión.
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // No se realiza ninguna acción al crear una sesión.
    }

    // Método que se ejecuta cuando se destruye una sesión.
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Obtiene la sesión HTTP asociada al evento.
        HttpSession session = se.getSession();
        // Recupera el carrito almacenado en la sesión.
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito != null) {
            // Devuelve los libros del carrito al stock.
            carritoService.devolverLibrosAlStock(carrito);
            // Elimina el carrito de la sesión.
            session.removeAttribute("carrito");
        }
    }
}
