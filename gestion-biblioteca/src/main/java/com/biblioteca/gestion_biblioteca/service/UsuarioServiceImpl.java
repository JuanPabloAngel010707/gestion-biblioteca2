// Declaración del paquete al que pertenece este archivo.
package com.biblioteca.gestion_biblioteca.service;

// Importa las clases necesarias para trabajar con los usuarios.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

import com.biblioteca.gestion_biblioteca.model.Usuario; // Importa la clase Usuario.
import com.biblioteca.gestion_biblioteca.repository.UsuarioRepository; // Importa el repositorio de usuarios.

@Service // Indica que esta clase es un servicio gestionado por Spring.
public class UsuarioServiceImpl implements UsuarioService {

    // Inyección de dependencia para interactuar con el repositorio y otros servicios.
    private final UsuarioRepository usuarioRepository;

    // Constructor con inyección de dependencias para inicializar el repositorio de usuarios.
    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método para obtener todos los usuarios registrados en la base de datos.
    @Override
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    // Método para buscar un usuario por su DNI. Devuelve un Optional para manejar valores nulos.
    @Override
    public Optional<Usuario> buscarPorDni(String dni) {
        return usuarioRepository.findById(dni);
    }

    // Método para crear un nuevo usuario. Si el usuario ya existe, lanza una excepción.
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioExistente(usuario.getDni())) {
            throw new IllegalArgumentException("El usuario con DNI " + usuario.getDni() + " ya existe.");
        }
        return usuarioRepository.save(usuario); // Guarda el nuevo usuario en la base de datos.
    }

    // Método para eliminar un usuario dado su DNI. Si no existe, lanza una excepción.
    @Override
    public void eliminarUsuario(String dni) {
        if (!usuarioExistente(dni)) {
            throw new IllegalArgumentException("El usuario con DNI " + dni + " no existe.");
        }
        usuarioRepository.deleteById(dni); // Elimina el usuario de la base de datos.
    }

    // Método para actualizar los datos de un usuario. Si el usuario no existe, lanza una excepción.
    @Override
    public Usuario actualizarUsuario(String dni, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = buscarPorDni(dni);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();

            // Actualiza los campos del usuario con los nuevos valores proporcionados.
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setDireccion(usuarioActualizado.getDireccion());

            // Guarda los cambios del usuario en la base de datos.
            return usuarioRepository.save(usuario);
        } else {
            throw new IllegalArgumentException("El usuario con DNI " + dni + " no existe.");
        }
    }

    // Método para verificar si un usuario con el DNI proporcionado existe.
    @Override
    public Boolean usuarioExistente(String dni) {
        Optional<Usuario> usuarioExistente = buscarPorDni(dni);
        if (usuarioExistente.isPresent()) {
            return true; // El usuario existe.
        }
        return false; // El usuario no existe.
    }
}
