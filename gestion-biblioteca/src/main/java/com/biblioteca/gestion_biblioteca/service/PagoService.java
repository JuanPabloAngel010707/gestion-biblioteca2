// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

import com.biblioteca.gestion_biblioteca.model.Factura; // Importa la clase Factura para manejar los pagos y cargos en el sistema.
import com.biblioteca.gestion_biblioteca.model.Pago; // Importa la clase Pago que se utilizará para registrar los pagos.
import com.biblioteca.gestion_biblioteca.model.Prestamo; // Importa la clase Prestamo para acceder a los préstamos relacionados con pagos.

import java.time.LocalDate; // Importa LocalDate para trabajar con fechas en el sistema.
import java.util.List; // Importa List para manejar colecciones de pagos.
import java.util.Optional; // Importa Optional para manejar valores nulos de forma segura.

// Interfaz que define las operaciones de negocio relacionadas con el procesamiento de pagos en el sistema.
public interface PagoService {

    // Método para buscar todos los pagos registrados.
    // Devuelve una lista con todos los pagos.
    List<Pago> buscarTodos();

    // Método para buscar un pago por su ID.
    // Devuelve un Optional que puede contener el pago si existe, o estar vacío si no se encuentra.
    Optional<Pago> buscarPorId(Long id);

    // Método para registrar un cargo relacionado con un préstamo.
    // Recibe el préstamo, la fecha de devolución y la cantidad devuelta.
    // Devuelve un objeto de tipo Pago.
    Pago registrarCargo(Prestamo prestamo, LocalDate fechaDevolucion, Integer cantidadDevuelta);

    // Método para registrar un pago realizado por un usuario relacionado con un préstamo.
    // Recibe el préstamo, la cantidad devuelta y el cargo correspondiente.
    // Devuelve un objeto de tipo Pago.
    Pago registrarPago(Prestamo prestamo, Integer cantidadDevuelta, Double cargo);

    // Método para realizar un pago y generar una factura.
    // Recibe una factura, realiza el pago y devuelve la factura actualizada.
    Factura realizarPago(Factura factura);
}
