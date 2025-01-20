package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Factura;
import com.biblioteca.gestion_biblioteca.model.Pago;
import com.biblioteca.gestion_biblioteca.model.Prestamo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PagoService {
	
    List<Pago> buscarTodos();

    Optional<Pago> buscarPorId(Long id);

    Pago registrarCargo(Prestamo prestamo, LocalDate fechaDevolucion, Integer cantidadDevuelta);

    Pago registrarPago(Prestamo prestamo, Integer cantidadDevuelta, Double cargo);

    Factura realizarPago(Factura factura);

}
