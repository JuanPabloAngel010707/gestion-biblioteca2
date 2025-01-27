// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importa las clases necesarias para trabajar con préstamos, libros y usuarios.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate; // Importa LocalDate para trabajar con fechas.

import com.biblioteca.gestion_biblioteca.model.Prestamo; // Importa la clase Prestamo para usarla en el servicio.
import com.biblioteca.gestion_biblioteca.model.Carrito;
import com.biblioteca.gestion_biblioteca.model.Factura;
import com.biblioteca.gestion_biblioteca.model.Libro; // Importa la clase Libro.
import com.biblioteca.gestion_biblioteca.model.Pago;
import com.biblioteca.gestion_biblioteca.model.Usuario; // Importa la clase Usuario.
import com.biblioteca.gestion_biblioteca.repository.PrestamoRepository; // Importa el repositorio de Prestamo.

import jakarta.transaction.Transactional;

import com.biblioteca.gestion_biblioteca.dto.DevolucionDTO;

@Service // Indica que esta clase es un servicio gestionado por Spring.
public class PrestamoServiceImpl implements PrestamoService {

    // Inyección de dependencias para interactuar con los repositorios y otros servicios.
    private final PrestamoRepository prestamoRepository;
    private final UsuarioService usuarioService;
    private final PagoService pagoService;
    private final StockService stockService;

    // Constructor para inyectar las dependencias necesarias.
    @Autowired
    public PrestamoServiceImpl(PrestamoRepository prestamoRepository, UsuarioService usuarioService, PagoService pagoService, StockService stockService) {
        this.prestamoRepository = prestamoRepository;
        this.usuarioService = usuarioService;
        this.pagoService = pagoService;
        this.stockService = stockService;
    }

    // Método para obtener todos los préstamos registrados en la base de datos.
    @Override
    public List<Prestamo> buscarTodos() {
        return prestamoRepository.findAll();
    }

    // Método para obtener los préstamos asociados a un libro utilizando su ISBN.
    @Override
    public List<Prestamo> buscarPorIsbnLibro(String isbnLibro) {
        return prestamoRepository.findByLibroIsbn(isbnLibro);
    }

    // Método para obtener los préstamos realizados por un usuario, dado su DNI.
    @Override
    public List<Prestamo> buscarPorDniUsuario(String dniUsuario) {
        return prestamoRepository.findByUsuarioDni(dniUsuario);
    }

    // Método para obtener un préstamo específico por su ID.
    @Override
    public Optional<Prestamo> buscarPorId(Long id) {
        return prestamoRepository.findById(id);
    }


    // Método para crear un nuevo préstamo basado en el carrito y el DNI del usuario.
    @Override
    public void crearPrestamo(Carrito carrito, String dniUsuario) {
        // Busca al usuario por su DNI, lanzando una excepción si no existe.
        Usuario usuario = usuarioService.buscarPorDni(dniUsuario).orElseThrow(() ->
                new IllegalArgumentException("El usuario con DNI " + dniUsuario + " no existe."));

        // Itera sobre los libros en el carrito, creando un préstamo por cada uno.
        for (Map.Entry<Libro, Integer> entry : carrito.obtenerLibros().entrySet()) {
            Libro libro = entry.getKey();
            Integer cantidadEnCarrito = entry.getValue();

            // Crea un objeto Prestamo y lo guarda en la base de datos.
            Prestamo prestamo = new Prestamo();
            prestamo.setLibro(libro);
            prestamo.setUsuario(usuario);
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamo.setEstado("activo");
            prestamo.setCantidad(cantidadEnCarrito);
            prestamo.setCantidadDevuelta(0);
            prestamoRepository.save(prestamo);
        }

        // Vacía el carrito después de crear los préstamos.
        carrito.vaciarCarrito();
    }

    // Método para eliminar un préstamo dado su ID.
    @Override
    public void eliminarPrestamo(Long id) {
        if (!prestamoExistente(id)) {
            throw new IllegalArgumentException("El préstamo con ID " + id + " no existe.");
        }
        prestamoRepository.deleteById(id); // Elimina el préstamo del repositorio.
    }


    // Método para procesar la devolución de préstamos.
    @Override
    @Transactional
    public Factura devolverPrestamo(String dniUsuario, List<DevolucionDTO> librosDevueltos) {
        Factura factura = new Factura(); // Crea una nueva factura para registrar los pagos.

        // Itera sobre los detalles de la devolución.
        for (DevolucionDTO devolucionDTO : librosDevueltos) {
            if (devolucionDTO.getPrestamoId() == null) {
                throw new IllegalArgumentException("El ID del préstamo no puede ser nulo.");
            }

            // Busca el préstamo correspondiente al ID proporcionado.
            Optional<Prestamo> prestamoExistente = buscarPorId(devolucionDTO.getPrestamoId());
            if (prestamoExistente.isPresent()) {
                Prestamo prestamo = prestamoExistente.get();

                // Valida el préstamo y procesa la devolución.
                validarPrestamoConDevolucion(prestamo, devolucionDTO, dniUsuario);

                // Registra el cargo asociado a la devolución.
                Pago pago = pagoService.registrarCargo(prestamo, LocalDate.now(), devolucionDTO.getCantidad());

                // Actualiza el stock del libro devuelto.
                stockService.aumentarCantidad(devolucionDTO.getLibroIsbn(), devolucionDTO.getCantidad());

                // Guarda el préstamo actualizado y agrega el pago a la factura.
                prestamoRepository.save(prestamo);
                factura.agregarPago(pago);
            } else {
                throw new IllegalArgumentException("El préstamo con ID " + devolucionDTO.getPrestamoId() + " no existe.");
            }
        }
        return pagoService.realizarPago(factura); // Finaliza el pago.
    }

    // Método para verificar si un préstamo existe dado su ID.
    @Override
    public Boolean prestamoExistente(Long id) {
        Optional<Prestamo> prestamoExistente = buscarPorId(id);
        return prestamoExistente.isPresent(); // Devuelve true si el préstamo existe.
    }

    // Método para validar un préstamo con base en una devolución.
    @Override
    public void validarPrestamoConDevolucion(Prestamo prestamo, DevolucionDTO devolucionDTO, String dniUsuario) {
        // Verifica que el préstamo pertenezca al usuario que realiza la devolución.
        if (!prestamo.getUsuario().getDni().equals(dniUsuario)) {
            throw new IllegalArgumentException("El préstamo con ID " + devolucionDTO.getPrestamoId() + " no pertenece a este usuario.");
        }

        // Verifica que el préstamo esté activo.
        if (!prestamo.getEstado().equals("activo")) {
            throw new IllegalArgumentException("El préstamo con ID " + devolucionDTO.getCantidad() + " ya ha sido devuelto.");
        }

        // Verifica que no se intenten devolver más ejemplares de los que se prestaron.
        if (prestamo.getCantidad() < devolucionDTO.getCantidad() + prestamo.getCantidadDevuelta()) {
            throw new IllegalArgumentException("No se puede devolver más ejemplares de lo que se ha tomado prestado.");
        } else if (prestamo.getCantidad() == devolucionDTO.getCantidad() + prestamo.getCantidadDevuelta()) {
            // Marca el préstamo como devuelto si se completó la devolución de todos los ejemplares.
            prestamo.setFechaDevolucion(LocalDate.now());
            prestamo.setEstado("devuelto");
            prestamo.setCantidadDevuelta(prestamo.getCantidad());
        } else {
            // Actualiza la cantidad devuelta si la devolución es parcial.
            prestamo.setCantidadDevuelta(devolucionDTO.getCantidad() + prestamo.getCantidadDevuelta());
        }
    }
}
