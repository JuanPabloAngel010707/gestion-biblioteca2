// Declaración del paquete al que pertenece esta clase.
package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*; // Importa las anotaciones y clases necesarias para JPA.
import java.time.LocalDate; // Importa la clase LocalDate para trabajar con fechas.

// Define la clase como una entidad de JPA que está mapeada a la tabla "pago".
@Entity
@Table(name = "pago")
public class Pago {

    // Identificador único del pago, generado automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación muchos-a-uno con la entidad Prestamo, que representa el préstamo asociado al pago.
    @ManyToOne
    @JoinColumn(name = "id_prestamo", nullable = false) // Define la clave foránea "id_prestamo".
    private Prestamo prestamo;

    // Columna que almacena la fecha en la que se realiza el pago.
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    // Columna que almacena el monto del pago.
    @Column(nullable = false)
    private Double monto;

    // Columna que almacena la cantidad de libros relacionados con el pago.
    @Column(nullable = false)
    private Integer cantidad_libros;

    // Columna que almacena el estado del pago.
    @Column(nullable = false)
    private String estado;

    // Constructor que permite inicializar un pago con todos sus atributos.
    public Pago(Prestamo prestamo, LocalDate fechaPago, Double monto, Integer cantidad_libros, String estado) {
        this.prestamo = prestamo;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.cantidad_libros = cantidad_libros;
        this.estado = estado;
    }

    // Métodos getter y setter para acceder y modificar los atributos de la clase.
    // Obtiene el identificador único del pago.
    public Long getId() {return id;}

    // Establece el identificador único del pago.
    public void setId(Long id) {
        this.id = id;
    }

    // Obtiene el préstamo asociado al pago.
    public Prestamo getPrestamo() {
        return prestamo;
    }

    // Establece el préstamo asociado al pago.
    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    // Obtiene la fecha del pago.
    public LocalDate getFechaPago() {
        return fechaPago;
    }

    // Establece la fecha del pago.
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    // Obtiene el monto del pago.
    public Double getMonto() {
        return monto;
    }

    // Establece el monto del pago.
    public void setMonto(Double monto) {
        this.monto = monto;
    }

    // Obtiene la cantidad de libros relacionados con el pago.
    public Integer getCantidadLibros() {
        return cantidad_libros;
    }

    // Establece la cantidad de libros relacionados con el pago.
    public void setCantidadLibros(Integer cantidadLibros) {
        this.cantidad_libros = cantidadLibros;
    }

    // Obtiene el estado del pago.
    public String getEstado() {
        return estado;
    }

    // Establece el estado del pago.
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
