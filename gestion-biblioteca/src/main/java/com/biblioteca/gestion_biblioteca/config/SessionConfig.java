package com.biblioteca.gestion_biblioteca.config;

import com.biblioteca.gestion_biblioteca.listener.SessionListener;
import com.biblioteca.gestion_biblioteca.service.CarritoService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {

    @Bean
    public SessionListener sessionListener(CarritoService carritoService) {
        return new SessionListener(carritoService);
    }
}