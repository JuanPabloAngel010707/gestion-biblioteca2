// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.repository;

// Importaciones necesarias para el manejo de repositorios JPA y las entidades correspondientes.
import com.biblioteca.gestion_biblioteca.model.AutorLibro;
import com.biblioteca.gestion_biblioteca.model.AutorLibroId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Anotación que marca esta interfaz como un repositorio JPA para la entidad AutorLibro.
@Repository
public interface AutorLibroRepository extends JpaRepository<AutorLibro, AutorLibroId> {
    // La interfaz extiende JpaRepository, lo que proporciona métodos CRUD (crear, leer, actualizar, eliminar) básicos.
    // El tipo de la entidad es AutorLibro y el tipo de la clave primaria es AutorLibroId.
}
