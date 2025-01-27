// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.repository;

// Importaciones necesarias para el manejo de repositorios JPA y las entidades correspondientes.
import com.biblioteca.gestion_biblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Importa la clase List para manejar colecciones de préstamos.

// Anotación que marca esta interfaz como un repositorio JPA para la entidad Prestamo.
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    // La interfaz extiende JpaRepository, lo que proporciona métodos CRUD (crear, leer, actualizar, eliminar) básicos.
    // El tipo de la entidad es Prestamo y el tipo de la clave primaria es Long.

    // Método personalizado para encontrar préstamos por estado.
    // 'findByEstado' devuelve una lista de préstamos que tienen el estado especificado.
    List<Prestamo> findByEstado(String estado);

    // Método personalizado para encontrar préstamos por el ISBN de un libro.
    // 'findByLibroIsbn' devuelve una lista de préstamos de un libro con el ISBN proporcionado.
    List<Prestamo> findByLibroIsbn(String isbn);

    // Método personalizado para encontrar préstamos por el DNI de un usuario.
    // 'findByUsuarioDni' devuelve una lista de préstamos hechos por un usuario con el DNI proporcionado.
    List<Prestamo> findByUsuarioDni(String dni);

    // Método personalizado para encontrar préstamos por el DNI de un usuario y estado.
    // 'findByUsuarioDniAndEstado' devuelve una lista de préstamos realizados por un usuario con el DNI proporcionado,
    // y que tienen el estado especificado.
    List<Prestamo> findByUsuarioDniAndEstado(String dniUsuario, String estado);

    // Método personalizado para encontrar préstamos por el ISBN de un libro y estado.
    // 'findByLibroIsbnAndEstado' devuelve una lista de préstamos de un libro con el ISBN proporcionado y que tienen el estado especificado.
    List<Prestamo> findByLibroIsbnAndEstado(String isbnLibro, String estado);

    // Método personalizado para encontrar préstamos por el ISBN de un libro, el DNI de un usuario y el estado.
    // 'findByLibroIsbnAndUsuarioDniAndEstado' devuelve una lista de préstamos que cumplen las siguientes condiciones:
        // - El libro tiene el ISBN proporcionado.
        // - El usuario tiene el DNI especificado.
        // - El préstamo tiene el estado indicado.
    List<Prestamo> findByLibroIsbnAndUsuarioDniAndEstado(String isbnLibro, String dniUsuario, String estado);
}


