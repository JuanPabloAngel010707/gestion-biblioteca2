// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importaciones necesarias para los modelos de AutorLibro, Autor y Libro.
import com.biblioteca.gestion_biblioteca.model.AutorLibro;
import com.biblioteca.gestion_biblioteca.model.AutorLibroId;
import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.model.Autor;

// Interfaz para el servicio que maneja las operaciones relacionadas con la entidad AutorLibro.
public interface AutorLibroService {

    // Método para crear una relación entre un libro y un autor.
    // Recibe como parámetros el libro y el autor para crear la relación correspondiente.
    AutorLibro crearAutorLibro(Libro libro, Autor autor);

    // Método para eliminar una relación entre un libro y un autor.
    // Recibe como parámetro el ID compuesto de la relación AutorLibro (AutorLibroId).
    void eliminarAutorLibro(AutorLibroId autorLibroId);
}
