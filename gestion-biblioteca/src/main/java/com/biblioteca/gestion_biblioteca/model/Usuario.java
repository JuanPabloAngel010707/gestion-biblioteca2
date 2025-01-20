// Declaración del paquete al que pertenece esta clase.
package com.biblioteca.gestion_biblioteca.model;

// Importaciones necesarias para manejar validaciones y persistencia en JPA.
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Anotación para marcar esta clase como una entidad gestionada por JPA.
// Representa una tabla en la base de datos.
@Entity
// Especifica el nombre de la tabla en la base de datos asociada a esta entidad.
@Table(name = "usuario")
public class Usuario {

    // Marca este campo como la clave primaria de la entidad 'Usuario'.
    @Id
    // Define que el DNI del usuario debe tener 8 dígitos y una letra al final.
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "El DNI debe tener 8 dígitos y una letra al final.")
    @NotBlank(message = "El DNI del usuario no puede estar vacío.")
    @NotNull(message = "El DNI del usuario no puede ser nulo.")
    @Column(name = "dni") // Especifica que 'dni' será el nombre de la columna en la tabla.
    private String dni;

    // Validaciones para el campo 'nombre' del usuario.
    @NotNull(message = "El nombre del usuario no puede ser nulo.")
    @NotBlank(message = "El nombre del usuario no puede estar vacío.")
    @Size(min = 2, message = "El nombre del usuario debe tener al menos 2 caracteres.")
    @Column // El nombre se mapea como una columna en la tabla 'usuario'.
    private String nombre;

    // Validaciones para el campo 'apellido' del usuario.
    @NotNull(message = "El apellido del usuario no puede ser nulo.")
    @NotBlank(message = "El apellido del usuario no puede estar vacío.")
    @Size(min = 2, message = "El apellido del usuario debe tener al menos 2 caracteres.")
    @Column // El apellido se mapea como una columna en la tabla 'usuario'.
    private String apellido;

    // Validaciones para el campo 'telefono' del usuario.
    @Pattern(regexp = "^\\+?[0-9\\s]+$", message = "El teléfono debe tener solo números y espacios (opcionalmente con '+' al inicio).")
    @NotNull(message = "El teléfono del usuario no puede ser nulo.")
    @NotBlank(message = "El teléfono del usuario no puede estar vacío.")
    @Column // El teléfono se mapea como una columna en la tabla 'usuario'.
    private String telefono;

    // Validaciones para el campo 'direccion' del usuario.
    @NotNull(message = "La dirección del usuario no puede ser nula.")
    @NotBlank(message = "La dirección del usuario no puede estar vacía.")
    @Size(min = 2, message = "La dirección del usuario debe tener al menos 5 caracteres.")
    @Column // La dirección se mapea como una columna en la tabla 'usuario'.
    private String direccion;

    // Métodos getter y setter para acceder y modificar los atributos privados.
    public String getDni() { return dni; } // Devuelve el DNI del usuario.
    public void setDni(String dni) { this.dni = dni; } // Establece el DNI del usuario.
    public String getNombre() { return nombre; } // Devuelve el nombre del usuario.
    public void setNombre(String nombre) { this.nombre = nombre; } // Establece el nombre del usuario.
    public String getApellido() { return apellido; } // Devuelve el apellido del usuario.
    public void setApellido(String apellido) { this.apellido = apellido; } // Establece el apellido del usuario.
    public String getTelefono() { return telefono; } // Devuelve el teléfono del usuario.
    public void setTelefono(String telefono) { this.telefono = telefono; } // Establece el teléfono del usuario.
    public String getDireccion() { return direccion; } // Devuelve la dirección del usuario.
    public void setDireccion(String direccion) { this.direccion = direccion; } // Establece la dirección del usuario.
}

