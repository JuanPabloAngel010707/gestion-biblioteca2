package com.biblioteca.gestion_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.biblioteca.gestion_biblioteca.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
public class StockController {
	
	private final StockService stockService;
	
	@Autowired
	public StockController(StockService stockService) {
		 
	    this.stockService = stockService;
    }
	
	@PutMapping("/reducir/{isbn}")
    public ResponseEntity<String> reducirCantidad(@PathVariable String isbn, @RequestParam Integer cantidad) {
        try {
            stockService.reducirCantidad(isbn, cantidad);
            return ResponseEntity.ok("Cantidad reducida correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/aumentar/{isbn}")
    public ResponseEntity<String> aumentarCantidad(@PathVariable String isbn, @RequestParam Integer cantidad) {
        try {
            stockService.aumentarCantidad(isbn, cantidad);
            return ResponseEntity.ok("Cantidad aumentada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
