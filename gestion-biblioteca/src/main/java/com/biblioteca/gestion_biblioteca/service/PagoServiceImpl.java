// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Pago; // Importa la clase Pago para manejar los pagos de los préstamos.
import com.biblioteca.gestion_biblioteca.model.Factura; // Importa la clase Factura para manejar los pagos a través de facturas.
import com.biblioteca.gestion_biblioteca.model.Prestamo; // Importa la clase Prestamo para acceder a los detalles del préstamo relacionado con el pago.
import com.biblioteca.gestion_biblioteca.repository.PagoRepository; // Importa el repositorio de Pago para interactuar con la base de datos.

import org.springframework.beans.factory.annotation.Autowired; // Importa la anotación @Autowired para la inyección de dependencias.
import org.springframework.stereotype.Service; // Marca esta clase como un servicio de Spring.

import java.time.LocalDate; // Importa LocalDate para trabajar con fechas.
import java.time.temporal.ChronoUnit; // Importa ChronoUnit para calcular la diferencia de días entre dos fechas.
import java.util.List; // Importa List para manejar colecciones de pagos.
import java.util.Optional; // Importa Optional para manejar valores nulos de forma segura.

// Anotación @Service para marcar esta clase como un servicio de Spring.
@Service
public class PagoServiceImpl implements PagoService {

    // Repositorio para gestionar las operaciones relacionadas con los pagos.
    @Autowired
    private PagoRepository pagoRepository;

    // Método para obtener todos los pagos registrados.
    public List<Pago> buscarTodos() {
        return pagoRepository.findAll();
    }

    // Método para buscar un pago por su ID.
    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    // Método para registrar un cargo basado en un préstamo, la fecha de devolución y la cantidad devuelta.
    public Pago registrarCargo(Prestamo prestamo, LocalDate fechaDevolucion, Integer cantidadDevuelta) {
        long diasPrestado = ChronoUnit.DAYS.between(prestamo.getFechaPrestamo(), fechaDevolucion);
        Double cargo = diasPrestado * 2.0 * cantidadDevuelta + 1;
        return registrarPago(prestamo, cantidadDevuelta, cargo);
    }

    // Método para actualizar un pago existente.
    public Pago actualizarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    // Método para registrar un nuevo pago asociado a un préstamo.
    public Pago registrarPago(Prestamo prestamo, Integer cantidadDevuelta, Double cargo) {
        Pago pago = new Pago(prestamo, LocalDate.now(), cargo, cantidadDevuelta, "pendiente");
        return pagoRepository.save(pago);
    }

    // Método para realizar un pago asociado a una factura.
    public Factura realizarPago(Factura factura) {
        try {
            factura.completarPagos();
            return factura;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ocurrió un error al realizar el pago.");
        }
    }
}
