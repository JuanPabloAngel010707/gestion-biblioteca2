// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Importa la anotación @Service para marcar esta clase como un servicio.
import java.util.List; // Importa la clase List para manejar colecciones de objetos.
import java.util.Optional; // Importa Optional para manejar valores que pueden ser nulos.
import com.biblioteca.gestion_biblioteca.model.Autor; // Importa la clase Autor.
import com.biblioteca.gestion_biblioteca.repository.AutorRepository; // Importa el repositorio de Autor.

// Anotación @Service marca esta clase como un componente de servicio de Spring, que se encarga de la lógica de negocio.
@Service
public class AutorServiceImpl implements AutorService {

    // Declaración de un campo privado para el repositorio de autores.
    private final AutorRepository autorRepository;

    // Constructor que inyecta el repositorio de autor en la clase.
    // Esta inyección de dependencias es gestionada por Spring.
    @Autowired
    public AutorServiceImpl(AutorRepository autorRepository, AutorLibroService autorLibroService) {
        this.autorRepository = autorRepository;
    }

    // Método para obtener todos los autores de la base de datos.
    @Override
    public List<Autor> buscarTodos() {
        return autorRepository.findAll(); // Devuelve una lista con todos los autores.
    }

    // Método para buscar autores por nombre. Ignora mayúsculas y minúsculas.
    @Override
    public List<Autor> buscarPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre); // Devuelve autores cuyo nombre contenga la cadena proporcionada.
    }

    // Método para buscar un autor por su ID. Devuelve un Optional, para manejar el caso de que el autor no exista.
    @Override
    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id); // Devuelve un Optional que puede contener o no el autor con el ID dado.
    }

    // Método para crear un nuevo autor en la base de datos.
    @Override
    public Autor crearAutor(Autor autor) {
        return autorRepository.save(autor); // Guarda el autor en la base de datos y lo devuelve.
    }

    // Método para eliminar un autor. Verifica primero si el autor existe.
    @Override
    public void eliminarAutor(Long id) {
        if (!autorExistente(id)) { // Si el autor no existe, lanza una excepción.
            throw new IllegalArgumentException("El autor con ID " + id + " no existe.");
        }
        autorRepository.deleteById(id); // Si el autor existe, lo elimina de la base de datos.
    }

    // Método para crear un autor (cuando se esta creando un libro) con el nombre proporcionado. Si ya existe, lo devuelve, si no, lo crea.
    @Override
    public Autor crearAutorConLibro(String nombreAutor) {
        // Busca autores con el nombre proporcionado.
        List<Autor> autores = buscarPorNombre(nombreAutor);

        // Si ya existen autores con ese nombre, devuelve el primero encontrado.
        if (!autores.isEmpty()) {
            return autores.get(0);
        } else {
            // Si no existe el autor, crea uno nuevo y lo guarda.
            Autor nuevoAutor = new Autor();
            nuevoAutor.setNombre(nombreAutor);

            return crearAutor(nuevoAutor); // Guarda el nuevo autor.
        }
    }

    // Método para verificar si un autor con el ID proporcionado ya existe.
    @Override
    public Boolean autorExistente(Long id) {
        Optional<Autor> autorExistente = buscarPorId(id); // Busca el autor por ID.
        // Si el autor existe, devuelve true, de lo contrario, false.
        if (autorExistente.isPresent()) {
            return true;
        }
        return false; // Si no existe, devuelve false.
    }
}
