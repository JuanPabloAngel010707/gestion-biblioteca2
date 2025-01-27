// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.dto;

// Clase que representa un DTO (Data Transfer Object) para las devoluciones de préstamos.
public class DevolucionDTO {

    private Long prestamoId; // Identificador del préstamo asociado a la devolución.
    private String libroIsbn; // ISBN del libro que se está devolviendo.
    private Integer cantidad; // Cantidad de ejemplares que se están devolviendo.

    // Constructor por defecto.
    public DevolucionDTO() {}

    // Constructor con parámetros para inicializar los atributos.
    public DevolucionDTO(String libroIsbn, Integer cantidad, Long prestamoId) {
        this.libroIsbn = libroIsbn;
        this.cantidad = cantidad;
        this.prestamoId = prestamoId;
    }

    // Getters y Setters
    public String getLibroIsbn() {
        return libroIsbn;
    } // Obtiene el Isbn del libro.
    public void setLibroIsbn(String libroIsbn) {
        this.libroIsbn = libroIsbn;
    } //Determina el Isbn de libro.
    public Integer getCantidad() {
        return cantidad;
    } // Obtiene la cantidad de ejemplares.
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    } // Determina la cantidad de ejemplares.
    public Long getPrestamoId() {
        return prestamoId;
    } // Obtiene el id del prestamo.
    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    } // Determina el id del prestamo.

}

