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
        if ("EXISTE".equals(result.getUsername())) {
            return "Este nombre de usuario ya está registrado";
        }
        return "Usuario agregado exitosamente";
    }

    @PutMapping("/{id}")
    public String updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Usuario result = usuarioService.updateUsuario(id, usuario);
        if (result == null) {
            return "No se encontró el usuario con esa ID";
        }
        return "Actualización exitosa";
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
    }
}