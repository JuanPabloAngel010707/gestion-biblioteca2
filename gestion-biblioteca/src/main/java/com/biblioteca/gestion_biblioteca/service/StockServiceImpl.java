package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.repository.LibroRepository;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
    private final LibroRepository libroRepository;
	private final LibroService libroService;
	
	@Autowired
    public StockServiceImpl(LibroRepository libroRepository, LibroService libroService) {
        this.libroRepository = libroRepository;
        this.libroService = libroService;

    }
	
    @Override
    public void reducirCantidad(String isbn, Integer cantidad ) {
    	
    	Optional<Libro> libroExistente = libroService.buscarPorIsbn(isbn);

        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            if (libro.getCantidad() - cantidad < 0) {
        		libro.setCantidad(0);
        	} else {
        		libro.setCantidad(libro.getCantidad() - cantidad);	
        	}
            
            libroRepository.save(libro);
        } else {
            
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " no existe.");
        }

    }
    
    @Override
    public void aumentarCantidad(String isbn, Integer cantidad) {
    	Optional<Libro> libroExistente = libroService.buscarPorIsbn(isbn);

        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            libro.setCantidad(libro.getCantidad() + cantidad);
            libroRepository.save(libro);

        } else {
            
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " no existe.");
        }
    

    }
    @Override  
    public Boolean suficienteStock(Libro libro, Integer cantidad, Integer cantidadCarrito) {
     	if (libro.getCantidad() + cantidadCarrito >= cantidad) {
     		
     		return true;
             
        } else {
        	return false;
   	
        } 
    }
    
}
