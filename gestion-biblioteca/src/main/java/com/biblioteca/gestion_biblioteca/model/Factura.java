// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.model;

import java.util.ArrayList; // Importa la clase ArrayList para manejar listas dinámicas.
import java.util.LinkedHashMap; // Importa LinkedHashMap para preservar el orden de los elementos.
import java.util.List; // Importa la interfaz List para definir listas.
import java.util.Map; // Importa la interfaz Map para manejar pares clave-valor.
import java.util.stream.Collectors; // Importa Collectors para realizar transformaciones en flujos de datos.

// Clase que representa una factura.
public class Factura {

    // Lista de pagos asociados a la factura.
    private List<Pago> pagos;

    // Total acumulado de la factura.
    private Double total;

    // Constructor que inicializa la lista de pagos y establece el total a 0.
    public Factura() {
        this.pagos = new ArrayList<>();
        this.total = 0.0;
    }

    // Método para agregar un pago a la factura.
    public void agregarPago(Pago pago) {
        pagos.add(pago); // Agrega el pago a la lista de pagos.
        total += pago.getMonto(); // Incrementa el total con el monto del pago.
    }

    // Método para establecer un valor total específico en la factura.
    public void setTotal(Double total) {
        this.total = total;
    }

    // Método para obtener el total acumulado de la factura.
    public double getTotal() {
        return total;
    }

    // Método para obtener la lista de pagos transformada en una lista de mapas.
    public List<Map<String, Object>> getPagos() {
        // Convierte cada pago en un mapa con las propiedades clave-valor deseadas.
        return pagos.stream().map(pago -> {
            Map<String, Object> pagoMap = new LinkedHashMap<>();
            pagoMap.put("FechaPago", pago.getFechaPago());
            pagoMap.put("idPago", pago.getId());
            pagoMap.put("dni", pago.getPrestamo().getUsuario().getDni());
            pagoMap.put("idPrestamo", pago.getPrestamo().getId());
            pagoMap.put("isbn", pago.getPrestamo().getLibro().getIsbn());
            pagoMap.put("cantidad", pago.getCantidadLibros());
            pagoMap.put("monto", pago.getMonto());
            return pagoMap;
        }).collect(Collectors.toList()); // Recopila todos los mapas en una lista.
    }

    // Método para vaciar la factura eliminando todos los pagos.
    public void vaciarFactura() {
        pagos.clear();
    }

    // Método para completar todos los pagos de la factura marcándolos como "completados".
    public void completarPagos() {
        for (Pago pago : pagos) {
            pago.setEstado("completado"); // Cambia el estado de cada pago a "completado".
        }
    }
}
