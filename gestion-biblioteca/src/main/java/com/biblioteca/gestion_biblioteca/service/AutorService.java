// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

import java.util.List;
import java.util.Optional; // Importa Optional para manejar valores que pueden ser nulos.
import com.biblioteca.gestion_biblioteca.model.Autor; // Importa la clase Autor para usarla como tipo en los métodos.

// Interfaz que define las operaciones que se pueden realizar sobre los autores.
// Estas operaciones serán implementadas por una clase de servicio.
public interface AutorService {

    // Método para buscar todos los autores en la base de datos.
    List<Autor> buscarTodos();

    // Método para buscar autores por nombre. Permite buscar autores cuyo nombre contenga el texto proporcionado.
    List<Autor> buscarPorNombre(String nombre);

    // Método para buscar un autor por su ID. Devuelve un Optional para manejar el caso en el que el autor no se encuentre.
    Optional<Autor> buscarPorId(Long id);

    // Método para crear un nuevo autor. Recibe un objeto Autor y lo guarda en la base de datos.
    Autor crearAutor(Autor autor);

    // Método para eliminar un autor. Recibe el ID del autor a eliminar.
    void eliminarAutor(Long id);

    // Método para crear un autor cuando se esta creando un libro.
    Autor crearAutorConLibro(String nombreAutor);

    // Método para verificar si un autor existe en la base de datos. Devuelve un valor booleano.
    Boolean autorExistente(Long id);
}
