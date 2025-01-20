package com.biblioteca.gestion_biblioteca.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo prestamo;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(nullable = false)
    private Double monto;
    
    @Column(nullable = false)
    private Integer cantidad_libros;

    @Column(nullable = false)
    private String estado;
    
    public Pago(Prestamo prestamo, LocalDate fechaPago, Double monto, Integer cantidad_libros, String estado) {
        this.prestamo = prestamo;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.cantidad_libros = cantidad_libros;
        this.estado = estado;
    }
    
    // Métodos getter y setter.
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Prestamo getPrestamo() {
        return prestamo;
    }
    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }
    public LocalDate getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
    public Double getMonto() {
        return monto;
    }
    public void setMonto(Double monto) {
        this.monto = monto;
    }
    
    public Integer getCantidadLibros() {
        return cantidad_libros;
    }
    public void setCantidadLibros(Integer cantidadLibros) {
        this.cantidad_libros = cantidadLibros;
    }
    
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
