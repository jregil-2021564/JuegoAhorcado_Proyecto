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