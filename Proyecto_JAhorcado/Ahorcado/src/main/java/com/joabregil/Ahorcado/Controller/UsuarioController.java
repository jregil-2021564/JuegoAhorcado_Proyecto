package com.joabregil.Ahorcado.Controller;

import com.joabregil.Ahorcado.model.Usuario;
import com.joabregil.Ahorcado.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    public String createUsuario(@RequestBody Usuario usuario) {
        Usuario result = usuarioService.saveUsuario(usuario);

        if ("ERROR_USERNAME_VACIO".equals(result.getUsername())) {
            return "El nombre de usuario no puede estar vacío.";
        }
        if ("ERROR_PASSWORD_VACIO".equals(result.getPassword())) {
            return "La contraseña no puede estar vacía.";
        }
        if ("ERROR_USERNAME_LARGO".equals(result.getUsername())) {
            return "El nombre de usuario no puede exceder los 100 caracteres.";
        }
        if ("ERROR_PASSWORD_LARGA".equals(result.getPassword())) {
            return "La contraseña no puede exceder los 100 caracteres.";
        }
        if ("EXISTE".equals(result.getUsername())) {
            return "Este nombre de usuario ya está registrado.";
        }

        return "Usuario agregado exitosamente";
    }

    @PutMapping("/{id}")
    public String updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Usuario result = usuarioService.updateUsuario(id, usuario);

        if (result == null) {
            return "No se encontró el usuario con esa ID.";
        }
        if ("ERROR_USERNAME_REPETIDO".equals(result.getUsername())) {
            return "No se puede actualizar, el nuevo nombre de usuario ya existe.";
        }
        if ("ERROR_USERNAME_LARGO".equals(result.getUsername())) {
            return "El nombre de usuario no puede exceder los 100 caracteres.";
        }
        if ("ERROR_PASSWORD_LARGA".equals(result.getPassword())) {
            return "La contraseña no puede exceder los 100 caracteres.";
        }
        if ("ERROR_USERNAME_VACIO".equals(result.getUsername())) {
            return "El nombre de usuario no puede estar vacío.";
        }
        if ("ERROR_PASSWORD_VACIO".equals(result.getPassword())) {
            return "La contraseña no puede estar vacía.";
        }

        return "Actualización exitosa";
    }

    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return "No se encontró el usuario con ese ID para ser eliminado.";
        }
        usuarioService.deleteUsuario(id);
        return "Usuario eliminado correctamente.";
    }
}