package com.biblioteca.gestion_biblioteca.listener;

import com.biblioteca.gestion_biblioteca.model.Carrito;
import com.biblioteca.gestion_biblioteca.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    @Autowired
    private CarritoService carritoService;
    
    public SessionListener(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
 
        HttpSession session = se.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito != null) {

            carritoService.devolverLibrosAlStock(carrito);

            session.removeAttribute("carrito");
        }
    }
}

