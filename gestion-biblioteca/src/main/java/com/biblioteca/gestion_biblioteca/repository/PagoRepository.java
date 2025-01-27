// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.repository;

// Importaciones necesarias para el manejo de repositorios JPA y las entidades correspondientes.
import com.biblioteca.gestion_biblioteca.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

// Interfaz que representa un repositorio JPA para la entidad Pago.
public interface PagoRepository extends JpaRepository<Pago, Long> {
    // La interfaz extiende JpaRepository, lo que proporciona métodos CRUD (crear, leer, actualizar, eliminar) básicos.
    // El tipo de la entidad es Pago y el tipo de la clave primaria es Long.
}
