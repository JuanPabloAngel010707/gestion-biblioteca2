// Declaración del paquete al que pertenece esta clase.
package com.biblioteca.gestion_biblioteca.model;

// Importaciones necesarias para manejar validaciones y persistencia en JPA.
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

// Anotación para marcar esta clase como una entidad gestionada por JPA, que corresponde a una tabla en la base de datos.
@Entity
// Especifica el nombre de la tabla en la base de datos asociada a esta entidad.
@Table(name = "libro")
public class Libro {

    // Marca este campo como la clave primaria de la entidad 'Libro'.
    @Id
    // Define que el campo 'isbn' debe cumplir con el patrón de un ISBN-13 sin guiones.
    @Pattern(regexp = "^(?:978|979)\\d{10}$", message = "El isbn debe cumplir con el formato ISBN-13, pero no tener guiones.")
    @NotBlank(message = "El isbn del libro no puede estar vacío.")
    @NotNull(message = "El isbn del libro no puede ser nulo.")
    @Column(name = "isbn") // Especifica que 'isbn' será el nombre de la columna en la tabla.
    private String isbn;

    // Validaciones para el campo 'titulo' del libro.
    @NotBlank(message = "El titulo del libro no puede estar vacío.")
    @NotNull(message = "El titulo del libro no puede ser nulo.")
    @Size(min = 2, max = 100, message = "El titulo del libro debe tener entre 2 y 100 caracteres.")
    @Column // El título se mapea como una columna en la tabla 'libro'.
    private String titulo;

    // Validaciones para el campo 'editorial' del libro.
    @NotBlank(message = "La editorial del libro no puede estar vacía.")
    @NotNull(message = "La editorial del libro no puede ser nula.")
    @Size(min = 2, max = 100, message = "La editorial del libro debe tener entre 2 y 100 caracteres.")
    @Column // La editorial se mapea como una columna en la tabla 'libro'.
    private String editorial;

    // Validaciones para el campo 'edicion' del libro.
    @NotBlank(message = "La edicion del libro no puede estar vacía.")
    @NotNull(message = "La edicion del libro no puede ser nula.")
    @Size(min = 2, max = 100, message = "La edicion del libro debe tener entre 2 y 100 caracteres.")
    @Column // La edición se mapea como una columna en la tabla 'libro'.
    private String edicion;

    // Validaciones para el campo 'anoPublicacion' del libro.
    @NotNull(message = "El año de publicación del libro no puede ser nulo.") // El año de publicación no puede ser nulo.
    @Min(value = 1800, message = "El año de publicación debe ser un número mayor o igual a 1800.")
    @Max(value = 2025, message = "El año de publicación debe ser un número menor o igual al año actual.") // O actualiza a un año actual
    @Column(name = "año_pblccn") // El año de publicación se mapea como una columna con nombre 'año_pblccn' en la tabla.
    private Integer anoPublicacion;
    
    @NotNull(message = "La cantidad de libros de este tipo no puede ser nula.") 
    @Min(value = 1, message = "La cantidad de libros de este tipo debe ser por lo menos 1.")
    @Column
    private Integer cantidad;

    // Relación uno-a-muchos con la entidad AutorLibro.
    // mappedBy: Indica que la relación está mapeada por el atributo 'libro' en AutorLibro.
    // cascade: Especifica que las operaciones de persistencia se propaguen a los elementos relacionados.
    // orphanRemoval: Elimina automáticamente los elementos relacionados huérfanos.
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    // Conjunto que representa la relación uno-a-muchos entre Libro y AutorLibro.
    private Set<AutorLibro> autorLibros;

    // Métodos getter y setter para acceder y modificar los atributos privados.
    public String getIsbn() { return isbn; } // Devuelve el ISBN del libro.
    public void setIsbn(String isbn) { this.isbn = isbn; } // Establece el ISBN del libro.
    public String getTitulo() {return titulo; } // Devuelve el título del libro.
    public void setTitulo(String titulo) { this.titulo = titulo; } // Establece el título del libro.
    public String getEditorial() { return editorial;} // Devuelve la editorial del libro.
    public void setEditorial(String editorial) { this.editorial = editorial; } // Establece la editorial del libro.
    public String getEdicion() { return edicion; } // Devuelve la edición del libro.
    public void setEdicion(String edicion) { this.edicion = edicion; } // Establece la edición del libro.
    public Integer getAnoPublicacion() { return anoPublicacion; } // Devuelve el año de publicación del libro.
    public void setAnoPublicacion(Integer anoPublicacion) { this.anoPublicacion = anoPublicacion; } // Establece el año de publicación del libro.
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
