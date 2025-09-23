package com.joabregil.Ahorcado.service;

import com.joabregil.Ahorcado.model.Usuario;
import com.joabregil.Ahorcado.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioServiceImplements implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImplements(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        // Validaciones manuales antes de guardar
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            usuario.setUsername("ERROR_USERNAME_VACIO");
            return usuario;
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            usuario.setPassword("ERROR_PASSWORD_VACIO");
            return usuario;
        }
        if (usuario.getUsername().length() > 100) {
            usuario.setUsername("ERROR_USERNAME_LARGO");
            return usuario;
        }
        if (usuario.getPassword().length() > 100) {
            usuario.setPassword("ERROR_PASSWORD_LARGA");
            return usuario;
        }

        // Validación de duplicados
        List<Usuario> lista = usuarioRepository.findAll();
        for (Usuario u : lista) {
            if (u.getUsername().equalsIgnoreCase(usuario.getUsername())) {
                usuario.setUsername("EXISTE");
                return usuario;
            }
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(id).orElse(null);
        if (existingUsuario != null) {
            // Validaciones manuales antes de actualizar
            if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
                usuario.setUsername("ERROR_USERNAME_VACIO");
                return usuario;
            }
            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                usuario.setPassword("ERROR_PASSWORD_VACIO");
                return usuario;
            }
            if (usuario.getUsername().length() > 100) {
                usuario.setUsername("ERROR_USERNAME_LARGO");
                return usuario;
            }
            if (usuario.getPassword().length() > 100) {
                usuario.setPassword("ERROR_PASSWORD_LARGA");
                return usuario;
            }

            // Validación de duplicados al actualizar
            List<Usuario> lista = usuarioRepository.findAll();
            for (Usuario u : lista) {
                if (!u.getCodigo_Usuario().equals(id)) {
                    if (u.getUsername().equalsIgnoreCase(usuario.getUsername())) {
                        usuario.setUsername("ERROR_USERNAME_REPETIDO");
                        return usuario;
                    }
                }
            }
            existingUsuario.setUsername(usuario.getUsername());
            existingUsuario.setPassword(usuario.getPassword());
            return usuarioRepository.save(existingUsuario);
        }
        return null;
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}