// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importa las clases necesarias para trabajar con los usuarios.
import java.util.Optional;
import java.util.List;
import com.biblioteca.gestion_biblioteca.model.Usuario; // Importa la clase Usuario.

public interface UsuarioService {

    // Método para obtener todos los usuarios registrados en la base de datos.
    List<Usuario> buscarTodos();

    // Método para buscar un usuario por su DNI. Devuelve un Optional para manejar el caso en que el usuario no exista.
    Optional<Usuario> buscarPorDni(String dni);

    // Método para crear un nuevo usuario. Recibe un objeto Usuario y lo guarda en la base de datos.
    Usuario crearUsuario(Usuario usuario);

    // Método para eliminar un usuario. Recibe el DNI del usuario a eliminar.
    void eliminarUsuario(String dni);

    // Método para actualizar la información de un usuario. Recibe el DNI y el nuevo objeto Usuario.
    Usuario actualizarUsuario(String dni, Usuario usuario);

    // Método para verificar si un usuario existe en la base de datos. Devuelve un valor booleano.
    Boolean usuarioExistente(String dni);
}
