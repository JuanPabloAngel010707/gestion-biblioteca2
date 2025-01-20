// Declaración del paquete al que pertenece esta clase.
package com.biblioteca.gestion_biblioteca.model;

// Importaciones necesarias para manejar la persistencia en JPA y trabajar con fechas.
import jakarta.persistence.*;
import java.time.LocalDate;

// Anotación para marcar esta clase como una entidad gestionada por JPA.
// Representa una tabla en la base de datos llamada 'prestamo'.
@Entity
// Especifica el nombre de la tabla en la base de datos asociada a esta entidad.
@Table(name = "prestamo")
public class Prestamo {

    // Marca este campo como la clave primaria de la entidad 'Prestamo'.
    // El valor será generado automáticamente mediante la estrategia de identidad, que en este caso, es la
    // base de datos la encargada.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación muchos-a-uno con la entidad 'Libro'.
    // @JoinColumn: Define la columna 'isbn_libro' en la tabla 'prestamo', que almacena la referencia al libro.
    // 'nullable = false' significa que este campo no puede ser nulo.
    @ManyToOne
    @JoinColumn(name = "isbn_libro", nullable = false)
    private Libro libro;

    // Relación muchos-a-uno con la entidad 'Usuario'.
    // @JoinColumn: Define la columna 'dni_usuario' en la tabla 'prestamo', que almacena la referencia al usuario.
    // 'nullable = false' significa que este campo no puede ser nulo.
    @ManyToOne
    @JoinColumn(name = "dni_usuario", nullable = false)
    private Usuario usuario;

    // Campo para almacenar la fecha en la que se realizó el préstamo del libro.
    // 'nullable = false' indica que este campo es obligatorio.
    @Column(name = "fecha_prstm", nullable = false)
    private LocalDate fechaPrestamo;

    // Campo para almacenar la fecha de devolución del libro.
    // Este campo es opcional y puede ser nulo.
    @Column(name = "fecha_dvlcn", nullable = true)
    private LocalDate fechaDevolucion;

    // Campo para almacenar el estado del préstamo ('activo', 'devuelto').
    // 'nullable = false' significa que este campo es obligatorio.
    @Column(nullable = false)
    private String estado;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = false)
    private Integer cantidadDevuelta;
    
    // Métodos getter y setter para acceder y modificar los atributos privados.
    public Long getId() { return id; } // Devuelve el ID del préstamo.
    public void setId(Long id) { this.id = id; } // Establece el ID del préstamo.
    public Libro getLibro() { return libro; } // Devuelve el libro asociado al préstamo.
    public void setLibro(Libro libro) { this.libro = libro; } // Establece el libro asociado al préstamo.
    public Usuario getUsuario() { return usuario; } // Devuelve el usuario que realiza el préstamo.
    public void setUsuario(Usuario usuario) { this.usuario = usuario; } // Establece el usuario que realiza el préstamo.
    public LocalDate getFechaPrestamo() { return fechaPrestamo; } // Devuelve la fecha de préstamo.
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; } // Establece la fecha de préstamo.
    public LocalDate getFechaDevolucion() { return fechaDevolucion; } // Devuelve la fecha de devolución del libro.
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; } // Establece la fecha de devolución del libro.
    public String getEstado() { return estado; } // Devuelve el estado del préstamo.
    public void setEstado(String estado) { this.estado = estado; } // Establece el estado del préstamo.
    public Integer getCantidad() { return cantidad; } 
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; } 
    public Integer getCantidadDevuelta() { return cantidadDevuelta; } 
    public void setCantidadDevuelta(Integer cantidadDevuelta) { this.cantidadDevuelta = cantidadDevuelta; } 
}
