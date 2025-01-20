package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Carrito;
import com.biblioteca.gestion_biblioteca.model.Libro;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoServiceImpl implements CarritoService{

    @Autowired
    private final LibroService libroService;
    private final StockService stockService;

    
    @Autowired
    public CarritoServiceImpl(LibroService libroService, StockService stockService) {

        this.libroService = libroService;
        this.stockService = stockService;

    }
    
    @Override
    public Map<String, Integer> verCarrito(Carrito carrito) {
        return carrito.obtenerLibrosIsbn();
    }
    
    @Override
    public void agregarLibroAlCarrito(Carrito carrito, String isbn, Integer cantidad) {
    	Optional<Libro> libroExistente = libroService.buscarPorIsbn(isbn);

        if (libroExistente.isPresent()) {
        	Optional<Libro> libroExistenteCarro = carrito.contieneLibroPorIsbn(isbn);

            if (libroExistenteCarro.isPresent()) {
            	actualizarCantidadLibroDelCarrito(carrito, isbn, cantidad);
            	 	        	
            } else {
            	
            	Libro libro = libroExistente.get();
                if (stockService.suficienteStock(libro, cantidad, 0)) {
                    carrito.agregarLibro(libro, cantidad);
                    stockService.reducirCantidad(isbn, cantidad);
                } else {
                
                	throw new IllegalArgumentException("No hay suficiente stock del libro con ISBN " + isbn);
                }     	
            }        
        } else {
        	throw new IllegalArgumentException("Libro con ISBN " + isbn + " no existe.");
        }
    }
    
    @Override
    public void quitarLibroDelCarrito(Carrito carrito, String isbn) {
    	Optional<Libro> libroExistente = carrito.contieneLibroPorIsbn(isbn);

        if (libroExistente.isPresent()) {
        	Libro libro = libroExistente.get();
        	stockService.aumentarCantidad(isbn, obtenerCantidadDeLibro(carrito, libro.getIsbn()));
            carrito.quitarLibro(libro);
        } else {
        	throw new IllegalArgumentException("Libro con ISBN " + isbn + " no se encuentra en el carrito.");       	
        }
    }
    
    @Override
    public void actualizarCantidadLibroDelCarrito(Carrito carrito, String isbn, Integer cantidad) {
    	Optional<Libro> libroExistente = carrito.contieneLibroPorIsbn(isbn);

        if (libroExistente.isPresent()) {
        	Libro libro = libroExistente.get();
        	Integer cantidadActual = obtenerCantidadDeLibro(carrito, libro.getIsbn());
        	
        	if (!stockService.suficienteStock(libro, cantidad, cantidadActual)) {
            
            	throw new IllegalArgumentException("No hay suficiente stock del libro con ISBN " + isbn);
            }  
        	if (cantidadActual < cantidad) {
        		
        		stockService.reducirCantidad(libro.getIsbn(), cantidad - cantidadActual);
        		
        	} else {
        		stockService.aumentarCantidad(libro.getIsbn(), cantidadActual - cantidad); 		
        	}
            carrito.actualizarCantidad(libro,cantidad);
        } else {
        	throw new IllegalArgumentException("Libro con ISBN " + isbn + " no se encuentra en el carrito.");       	
        }
    }
    
    @Override
    public void devolverLibrosAlStock(Carrito carrito) {
        
    	for (Map.Entry<Libro, Integer> entry : carrito.obtenerLibros().entrySet()) {
    		Libro libro = entry.getKey(); 
    		Integer cantidadEnCarrito = entry.getValue();
    		
    		stockService.aumentarCantidad(libro.getIsbn(), cantidadEnCarrito);

        }
    }
    
    @Override
    public Integer obtenerCantidadDeLibro(Carrito carrito, String isbn) {
        
        for (Map.Entry<Libro, Integer> entry : carrito.obtenerLibros().entrySet()) {
            Libro libro = entry.getKey();
            if (libro.getIsbn().equals(isbn)) {               
                return entry.getValue();
            }
        }
        return 0;
    }
    
    @Override
    public void vaciarCarrito(Carrito carrito) {
        carrito.vaciarCarrito();
    }
}
