// Declaración del paquete al que pertenece esta clase.
package com.biblioteca.gestion_biblioteca.model;

// Importación necesaria para marcar esta clase como embebible en JPA.
import jakarta.persistence.Embeddable;

// Importaciones necesarias para implementar Serializable y métodos hash/equal.
import java.io.Serializable;
import java.util.Objects;

// Anotación que indica que esta clase es embebible y puede ser utilizada como parte de una clave primaria compuesta en JPA.
@Embeddable
public class AutorLibroId implements Serializable {

    // Atributo que representa el identificador del libro.
    // Este campo será parte de la clave primaria compuesta.
    private String libroId;

    // Atributo que representa el identificador del autor.
    // Este campo también será parte de la clave primaria compuesta.
    private Long autorId;

    // Constructor vacío requerido por JPA.
    public AutorLibroId() {}

    // Constructor que inicializa ambos campos de la clave primaria compuesta.
    public AutorLibroId(String libroId, Long autorId) {
        this.libroId = libroId;
        this.autorId = autorId;
    }

    // Métodos getter y setter para el acceso y modificación de los atributos privados.
    public String getLibroId() {
        return libroId;
    } // Obtiene el identificador del libro.
    public void setLibroId(String libroId) {
        this.libroId = libroId;
    } // Establece el identificador del libro.
    public Long getAutorId() {
        return autorId;
    } // Obtiene el identificador del autor.
    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    } // Establece el identificador del autor.

    // Método para comparar si dos objetos de esta clase son iguales.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Compara referencias.
        if (o == null || getClass() != o.getClass()) return false; // Verifica clase y nulidad.
        AutorLibroId that = (AutorLibroId) o;
        // Compara los valores de libroId y autorId.
        return Objects.equals(libroId, that.libroId) && Objects.equals(autorId, that.autorId);
    }

    // Genera un código hash basado en los atributos libroId y autorId.
    @Override
    public int hashCode() {
        return Objects.hash(libroId, autorId);
    }
}

