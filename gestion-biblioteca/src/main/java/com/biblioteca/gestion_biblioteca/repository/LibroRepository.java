// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.repository;

// Importaciones necesarias para el manejo de repositorios JPA y las entidades correspondientes.
import com.biblioteca.gestion_biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Importa la clase List para manejar colecciones de libros.

// Anotación que marca esta interfaz como un repositorio JPA para la entidad Libro.
@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {
    // La interfaz extiende JpaRepository, lo que proporciona métodos CRUD (crear, leer, actualizar, eliminar) básicos.
    // El tipo de la entidad es Libro y el tipo de la clave primaria es String.

    // Método personalizado para encontrar libros por título, ignorando mayúsculas y minúsculas.
    // 'Containing' permite buscar libros cuyo título contenga el texto proporcionado.
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Método personalizado para encontrar libros por el nombre del autor, ignorando mayúsculas y minúsculas.
    // 'findByAutorLibrosAutorNombreContainingIgnoreCase' accede a través de la relación AutorLibro.
    List<Libro> findByAutorLibrosAutorNombreContainingIgnoreCase(String nombreAutor);
}
