// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.repository;

// Importaciones necesarias para el manejo de repositorios JPA y las entidades correspondientes.
import com.biblioteca.gestion_biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Importa la clase List para manejar colecciones de autores.

// Anotación que marca esta interfaz como un repositorio JPA para la entidad Autor.
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    // La interfaz extiende JpaRepository, lo que proporciona métodos CRUD (crear, leer, actualizar, eliminar) básicos.
    // El tipo de la entidad es Autor y el tipo de la clave primaria es Long.

    // Método personalizado para encontrar autores por nombre, ignorando mayúsculas y minúsculas.
    // 'Containing' permite buscar autores cuyo nombre contenga el texto proporcionado.
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
}


