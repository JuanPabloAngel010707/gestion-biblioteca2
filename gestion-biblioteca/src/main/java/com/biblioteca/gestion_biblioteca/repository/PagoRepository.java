package com.biblioteca.gestion_biblioteca.repository;

import com.biblioteca.gestion_biblioteca.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}

