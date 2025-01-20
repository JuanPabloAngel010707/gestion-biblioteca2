package com.biblioteca.gestion_biblioteca.dto;

public class DevolucionDTO {
	
	private Long prestamoId;
    private String libroIsbn;    
    private Integer cantidad;    

    
    public DevolucionDTO() {}

    
    public DevolucionDTO(String libroIsbn, Integer cantidad, Long prestamoId) {
        this.libroIsbn = libroIsbn;
        this.cantidad = cantidad;
        this.prestamoId = prestamoId;
    }

    // Getters y Setters
    public String getLibroIsbn() {
        return libroIsbn;
    }

    public void setLibroIsbn(String libroIsbn) {
        this.libroIsbn = libroIsbn;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

}

