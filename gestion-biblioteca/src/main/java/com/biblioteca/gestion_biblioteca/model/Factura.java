package com.biblioteca.gestion_biblioteca.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Factura {
 private List<Pago> pagos; 
 private Double total;

 public Factura() {
     this.pagos = new ArrayList<>();
     this.total = 0.0;
 }


 public void agregarPago(Pago pago) {
     pagos.add(pago);
     total += pago.getMonto();
 }
 
 public void setTotal(Double total) {
	 this.total = total;
 }

 public double getTotal() {
     return total;
 }

 public List<Map<String, Object>> getPagos() {
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
	    }).collect(Collectors.toList());
	}
 
 public void vaciarFactura() {
     pagos.clear();  
 }
 
 public void completarPagos() {
	 
	 for (Pago pago : pagos) {
	        pago.setEstado("completado");  
	    }
	 
 }
}
