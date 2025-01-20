// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.repository;

// Importaciones necesarias para el manejo de repositorios JPA y las entidades correspondientes.
import com.biblioteca.gestion_biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Anotación que marca esta interfaz como un repositorio JPA para la entidad Usuario.
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // La interfaz extiende JpaRepository, lo que proporciona métodos CRUD (crear, leer, actualizar, eliminar) básicos.
    // El tipo de la entidad es Usuario y el tipo de la clave primaria es String (en este caso, el DNI del usuario).
}
