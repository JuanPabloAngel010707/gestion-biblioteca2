package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Pago;
import com.biblioteca.gestion_biblioteca.model.Factura;
import com.biblioteca.gestion_biblioteca.model.Prestamo;
import com.biblioteca.gestion_biblioteca.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    
    public List<Pago> buscarTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }
    
    public Pago registrarCargo(Prestamo prestamo, LocalDate fechaDevolucion, Integer cantidadDevuelta) { 	
        long diasPrestado = ChronoUnit.DAYS.between(prestamo.getFechaPrestamo(), fechaDevolucion);
        Double cargo = diasPrestado * 2.0 * cantidadDevuelta + 1;        
        return registrarPago(prestamo, cantidadDevuelta, cargo);  
    }
    
    public Pago actualizarPago(Pago pago) {
        return pagoRepository.save(pago);
    }
    
    public Pago registrarPago(Prestamo prestamo, Integer cantidadDevuelta, Double cargo) {
    	Pago pago = new Pago(prestamo, LocalDate.now(), cargo, cantidadDevuelta, "pendiente");  
        return pagoRepository.save(pago);
    }
    
    public Factura realizarPago(Factura factura) {
    	
        try {
        	factura.completarPagos();
        	return factura;
        } catch (IllegalArgumentException e) {
        	throw new IllegalArgumentException("Ocurrió un error al realizar el pago.");
        }
    		
    }
}
