// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importa las clases necesarias para trabajar con los repositorios y servicios de la aplicación.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa las clases necesarias para manejar libros, autores y préstamos.
import java.util.List;
import java.util.Optional;
import com.biblioteca.gestion_biblioteca.model.Libro;
import com.biblioteca.gestion_biblioteca.model.Autor;
import com.biblioteca.gestion_biblioteca.model.Prestamo;

// Importa los repositorios necesarios para interactuar con la base de datos.
import com.biblioteca.gestion_biblioteca.repository.LibroRepository;
import com.biblioteca.gestion_biblioteca.repository.PrestamoRepository;

// Anotación que marca esta clase como un servicio de Spring.
@Service
public class LibroServiceImpl implements LibroService {

    // Inyecciones de dependencias para los repositorios y servicios necesarios.
    private final LibroRepository libroRepository;
    private final AutorService autorService;
    private final AutorLibroService autorLibroService;
    private final PrestamoRepository prestamoRepository;

    // Constructor de la clase donde se inicializan los servicios y repositorios inyectados.
    @Autowired
    public LibroServiceImpl(LibroRepository libroRepository, AutorService autorService, AutorLibroService autorLibroService, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.autorService = autorService;
        this.autorLibroService = autorLibroService;
        this.prestamoRepository = prestamoRepository;
    }

    // Método para obtener todos los libros de la base de datos.
    @Override
    public List<Libro> buscarTodos() {
        return libroRepository.findAll();
    }

    // Método para buscar libros por su título. Ignora las mayúsculas y minúsculas al buscar.
    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Método para buscar libros por el nombre de su autor. Ignora las mayúsculas y minúsculas.
    @Override
    public List<Libro> buscarPorAutor(String autorNombre) {
        return libroRepository.findByAutorLibrosAutorNombreContainingIgnoreCase(autorNombre);
    }

    // Método para buscar un libro por su ISBN. Retorna un Optional para manejar el caso en el que el libro no exista.
    @Override
    public Optional<Libro> buscarPorIsbn(String isbn) {
        return libroRepository.findById(isbn);
    }

    // Método para crear un libro. Si el libro no existe, se guarda en la base de datos y se asocia con un autor.
    @Override
    public Libro crearLibro(Libro libro, String nombreAutor) {

        // Verifica si el libro con el mismo ISBN ya existe. Si existe, lanza una excepción.
        if (libroExistente(libro.getIsbn())) {
            throw new IllegalArgumentException("El libro con ISBN " + libro.getIsbn() + " ya existe.");
        }

        // Guarda el libro en la base de datos.
        Libro libroGuardado = libroRepository.save(libro);

        // Crea o encuentra el autor asociado al libro.
        Autor autor = autorService.crearAutorConLibro(nombreAutor);

        // Asocia el libro con el autor en la tabla intermedia.
        autorLibroService.crearAutorLibro(libroGuardado, autor);

        // Devuelve el libro guardado.
        return libroGuardado;
    }

    // Método para eliminar un libro. Verifica si el libro tiene préstamos activos antes de eliminarlo.
    @Override
    public void eliminarLibro(String isbn) {
        // Verifica si el libro existe. Si no existe, lanza una excepción.
        if (!libroExistente(isbn)) {
            throw new IllegalArgumentException("El libro con ISBN " + isbn + " no existe.");
        }

        // Busca los préstamos activos para el libro con el ISBN proporcionado.
        List<Prestamo> prestamosActivos = prestamoRepository.findByLibroIsbnAndEstado(isbn, "activo");

        // Si hay préstamos activos, no se puede eliminar el libro.
        if (!prestamosActivos.isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar el libro con ISBN " + isbn +
                    " porque tiene préstamos activos. ");
        }

        // Elimina el libro de la base de datos.
        libroRepository.deleteById(isbn);
    }

    // Método para actualizar un libro existente. Si el libro no existe, lanza una excepción.
    @Override
    public Libro actualizarLibro(String isbn, Libro libroActualizado) {
        // Busca el libro por su ISBN.
        Optional<Libro> libroExistente = buscarPorIsbn(isbn);

        // Si el libro existe, actualiza sus datos.
        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();

            // Actualiza los campos del libro con la nueva información.
            libro.setTitulo(libroActualizado.getTitulo());
            libro.setEditorial(libroActualizado.getEditorial());
            libro.setEdicion(libroActualizado.getEdicion());
            libro.setAnoPublicacion(libroActualizado.getAnoPublicacion());

            // Guarda los cambios en la base de datos y devuelve el libro actualizado.
            return libroRepository.save(libro);
        } else {
            // Si el libro no existe, lanza una excepción.
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " no existe.");
        }

    }

    // Método para verificar si un libro con el ISBN proporcionado ya existe en la base de datos.
    @Override
    public Boolean libroExistente(String isbn) {
        // Busca el libro por su ISBN.
        Optional<Libro> libroExistente = buscarPorIsbn(isbn);

        // Si el libro existe, devuelve true.
        if (libroExistente.isPresent()) {
            return true;
        }
        // Si el libro no existe, devuelve false.
        return false;
    }

}
