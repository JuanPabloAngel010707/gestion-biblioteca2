/*
Alumnos:
Juan Pablo Angel Gonzalez
Mariana Catalina Pemberthy Mantilla
*/

// Declaración del paquete al que pertenece esta clase.
package com.biblioteca.gestion_biblioteca;

// Importación de clases necesarias para ejecutar una aplicación Spring Boot.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Clase principal de la aplicación Spring Boot
@SpringBootApplication
public class GestionBibliotecaApplication {

	// Método principal de la aplicación donde SpringApplication.run inicia la aplicación, configurando automáticamente
	// los componentes definidos.
	public static void main(String[] args) {
		SpringApplication.run(GestionBibliotecaApplication.class, args);
	}
}
