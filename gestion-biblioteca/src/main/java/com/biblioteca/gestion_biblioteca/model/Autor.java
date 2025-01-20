// Declaración del paquete al que pertenece esta clase.
package com.biblioteca.gestion_biblioteca.model;

// Importaciones necesarias para el manejo de persistencia y validaciones.
// jakarta.persistence.*: Proporciona las anotaciones para definir entidades y sus propiedades en JPA (Java Persistence API).
// jakarta.validation.constraints.*: Define restricciones para la validación de datos, como @NotBlank, @NotNull, @Size.
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

// Anotación para marcar esta clase como una entidad gestionada por JPA.
// Representa una tabla en la base de datos.
@Entity
// Especifica el nombre de la tabla en la base de datos asociada a esta entidad.
@Table(name = "autor")
public class Autor {

    // Anotación que marca este campo como la clave primaria de la entidad.
    @Id
    // Define la estrategia para la generación automática de valores de la clave primaria, que en este caso, es la
    // base de datos la encargada.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Validaciones para el campo 'nombre'.
    // @NotBlank: El campo no puede estar vacío ni contener solo espacios en blanco.
    // @NotNull: El campo no puede ser nulo.
    // @Size: Define el tamaño mínimo y máximo permitido para el nombre.
    @NotBlank(message = "El nombre del autor no puede estar vacío.")
    @NotNull(message = "El nombre del autor no puede ser nulo.")
    @Size(min = 2, max = 100, message = "El nombre del autor debe tener entre 2 y 100 caracteres.")

    // Define que este campo se mapeará como una columna en la tabla 'autor'
    @Column
    private String nombre;

    // Relación uno-a-muchos con la entidad AutorLibro.
    // mappedBy: Indica que la relación está mapeada por el atributo 'autor' en AutorLibro.
    // cascade: Especifica que las operaciones de persistencia se propaguen a los elementos relacionados.
    // orphanRemoval: Elimina automáticamente los elementos relacionados huérfanos.
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    // Conjunto que representa la relación uno-a-muchos entre Autor y AutorLibro.
    private Set<AutorLibro> autorLibros;

    // Métodos getter y setter para el acceso y modificación de los atributos privados.
    public Long getId() {
        return id;
    } // Obtiene el id.
    public void setId(Long id) {
        this.id = id;
    } // Determina el id.
    public String getNombre() {
        return nombre;
    } // Obtiene el nombre.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    } // Determina el nombre.
}

