// Declaración del paquete al que pertenece esta clase.
package com.biblioteca.gestion_biblioteca.model;

// Importaciones necesarias para el manejo de persistencia con JPA.
import jakarta.persistence.*;

// Anotación para marcar esta clase como una entidad gestionada por JPA.
// Representa una tabla en la base de datos.
@Entity
@Table(name = "autor_libro")  
public class AutorLibro {

    // Anotación que indica que este campo es una clave primaria compuesta.
    // La clase 'AutorLibroId' define la estructura de dicha clave primaria.
	@EmbeddedId
    private AutorLibroId id;

    // Relación muchos-a-uno con la entidad 'Libro'.
    // @MapsId: Asocia esta entidad con un campo de la clave primaria compuesta en 'AutorLibroId'.
    // @JoinColumn: Especifica la columna 'isbn_libro' como clave foránea, que no puede ser nula.
    @ManyToOne
    @MapsId("libroId")
    @JoinColumn(name = "isbn_libro", nullable = false)
    private Libro libro;

    // Relación muchos-a-uno con la entidad 'Autor'.
    // @MapsId: Asocia esta entidad con un campo de la clave primaria compuesta en 'AutorLibroId'.
    // @JoinColumn: Especifica la columna 'id_autor' como clave foránea, que no puede ser nula.
    @ManyToOne
    @MapsId("autorId")
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    // Métodos getter y setter para el acceso y modificación de los atributos privados.
    public AutorLibroId getId() {
        return id;
    }  // Obtiene la clave primaria compuesta.
    public void setId(AutorLibroId id) {
        this.id = id;
    } // Establece la clave primaria compuesta.
    public Autor getAutor() {
        return autor;
    } // Obtiene el autor asociado a este registro.
    public void setAutor(Autor autor) {
        this.autor = autor;
    } // Establece el autor asociado a este registro.
    public Libro getLibro() {
        return libro;
    } // Obtiene el libro asociado a este registro.
    public void setLibro(Libro libro) {
        this.libro = libro;
    } // Establece el libro asociado a este registro.
}

