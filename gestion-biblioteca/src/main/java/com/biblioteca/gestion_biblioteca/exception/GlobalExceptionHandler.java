package com.biblioteca.gestion_biblioteca.exception;

//Importaciones necesarias para manejar excepciones, construir respuestas HTTP y trabajar con anotaciones.
import org.springframework.http.ResponseEntity; // Permite construir respuestas HTTP personalizadas.
import org.springframework.http.HttpStatus; // Proporciona los códigos de estado HTTP estándar.
import org.springframework.web.bind.annotation.ExceptionHandler; // Anotación para manejar excepciones específicas.
import org.springframework.web.bind.annotation.RestControllerAdvice; // Marca la clase como manejador global de excepciones.

//Importacion de clases para manejar validaciones y excepciones específicas
import jakarta.validation.ConstraintViolationException; // Excepción para violaciones de restricciones en validaciones.
import org.springframework.web.bind.MissingServletRequestParameterException; // Excepción para parámetros faltantes en solicitudes HTTP
import org.springframework.web.bind.MethodArgumentNotValidException; // Excepción para argumentos no válidos en métodos


import java.util.HashMap; // Clase para crear mapas de clave-valor
import java.util.Map; // Interfaz para representar mapas


// Clase GlobalExceptionHandler que gestiona excepciones globalmente como cuando no se pasa un parametro o los datos pasados
// no son validos.
// Utiliza @RestControllerAdvice para interceptar excepciones lanzadas por los controladores y proporcionar
// respuestas personalizadas.
 
@RestControllerAdvice
public class GlobalExceptionHandler {

	// Metodo que maneja excepciones de tipo MissingServletRequestParameterException.
    // Esta excepción ocurre cuando falta un parámetro requerido en la solicitud.
	@ExceptionHandler(MissingServletRequestParameterException.class) // Indica que este método maneja esta excepción específica
    public ResponseEntity<String> handleMissingParam(MissingServletRequestParameterException ex) { 
		// Construye un mensaje indicando qué parámetro falta
        String message = "Error: El parámetro requerido '" + ex.getParameterName() + "' no está presente."; 
     // Devuelve una respuesta HTTP con el mensaje y un estado 400.
        return ResponseEntity.badRequest().body(message);
    }
	
	// Metodo que maneja la excepción MethodArgumentNotValidException.
    // Esta excepción ocurre cuando los argumentos del método no cumplen con las validaciones especificadas.
	@ExceptionHandler(MethodArgumentNotValidException.class) // Indica que este método maneja esta excepción específica
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		// Crea un mapa para almacenar los errores de validación.
		Map<String, String> errors = new HashMap<>();
		// Itera sobre los errores de validación y los agrega al mapa.
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())); // Clave: nombre del campo, Valor: mensaje de error.
        // Devuelve una respuesta HTTP con los errores y un estado 400
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
	
	// Metodo que maneja la excepción ConstraintViolationException.
    // Esta excepción ocurre cuando se violan restricciones de validación (por ejemplo, límites de valores).
	@ExceptionHandler(ConstraintViolationException.class) // Indica que este método maneja esta excepción específica.
    public ResponseEntity<Map<String, String>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
		// Crea un mapa para almacenar los errores de restricción.
		Map<String, String> errors = new HashMap<>();
		// Itera sobre las violaciones y las agrega al mapa.
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString(); // Obtiene el nombre del campo que violó la restricción.
            String errorMessage = violation.getMessage(); // Obtiene el mensaje de error.
            errors.put(fieldName, errorMessage); // Agrega el campo y el mensaje al mapa.
        });
        // Devuelve una respuesta HTTP con los errores y un estado 400.
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
