// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importaciones necesarias para los componentes de Spring, transacciones, repositorios y modelos.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.biblioteca.gestion_biblioteca.repository.AutorLibroRepository;
import com.biblioteca.gestion_biblioteca.model.Autor;
import com.biblioteca.gestion_biblioteca.model.AutorLibro;
import com.biblioteca.gestion_biblioteca.model.AutorLibroId;
import com.biblioteca.gestion_biblioteca.model.Libro;

// Anotación que indica que esta clase es un servicio gestionado por Spring.
@Service
public class AutorLibroServiceImpl implements AutorLibroService {

    // Repositorio de AutorLibro, utilizado para interactuar con la base de datos.
    private final AutorLibroRepository autorLibroRepository;

    // Constructor para inyectar el repositorio de AutorLibro.
    @Autowired
    public AutorLibroServiceImpl(AutorLibroRepository autorLibroRepository) {
        this.autorLibroRepository = autorLibroRepository;
    }

    // Método para crear una relación entre un libro y un autor.
    // La anotación @Transactional asegura que la operación sea atómica (si algo falla, todo se revierte).
    @Transactional
    @Override
    public AutorLibro crearAutorLibro(Libro libro, Autor autor) {
        // Se crea un ID compuesto utilizando el ISBN del libro y el ID del autor.
        AutorLibroId autorLibroId = new AutorLibroId(libro.getIsbn(), autor.getId());

        // Se crea un objeto AutorLibro que representa la relación entre el libro y el autor.
        AutorLibro autorLibro = new AutorLibro();

        // Se establece el ID compuesto, el libro y el autor en el objeto AutorLibro.
        autorLibro.setId(autorLibroId);
        autorLibro.setLibro(libro);
        autorLibro.setAutor(autor);

        // Se guarda la relación AutorLibro en la base de datos.
        return autorLibroRepository.save(autorLibro);
    }

    // Método para eliminar una relación entre un libro y un autor.
    // Recibe el ID compuesto de la relación (AutorLibroId) y elimina la relación correspondiente de la base de datos.
    @Override
    public void eliminarAutorLibro(AutorLibroId autorLibroId) {
        // Elimina la relación AutorLibro de la base de datos utilizando su ID compuesto.
        autorLibroRepository.deleteById(autorLibroId);
    }
}
