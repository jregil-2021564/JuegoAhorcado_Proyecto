package com.joabregil.Ahorcado.service;

import com.joabregil.Ahorcado.model.Palabra;
import com.joabregil.Ahorcado.repository.PalabraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalabraServiceImplements implements PalabraService {

    private final PalabraRepository palabraRepository;

    public PalabraServiceImplements(PalabraRepository palabraRepository) {
        this.palabraRepository = palabraRepository;
    }

    @Override
    public List<Palabra> getAllPalabras() {
        return palabraRepository.findAll();
    }

    @Override
    public Palabra getPalabraById(Integer id) {
        return palabraRepository.findById(id).orElse(null);
    }

    @Override
    public Palabra savePalabra(Palabra palabra) {
        // Validación: no permitir palabras vacías o muy cortas
        if (palabra.getPalabra() == null || palabra.getPalabra().trim().length() < 3) {
            Palabra resultado = new Palabra();
            resultado.setPalabra("ERROR_PALABRA_INVALIDA");
            return resultado;
        }

        // Validación: pista no puede estar vacía
        if (palabra.getPista() == null || palabra.getPista().trim().isEmpty()) {
            Palabra resultado = new Palabra();
            resultado.setPalabra("ERROR_PISTA_VACIA");
            return resultado;
        }

        return palabraRepository.save(palabra);
    }

    @Override
    public Palabra updatePalabra(Integer id, Palabra palabra) {
        Palabra existingPalabra = palabraRepository.findById(id).orElse(null);
        if (existingPalabra != null) {
            // Validar palabra
            if (palabra.getPalabra() == null || palabra.getPalabra().trim().length() < 3) {
                palabra.setPalabra("ERROR_PALABRA_INVALIDA");
                return palabra;
            }

            // Validar pista
            if (palabra.getPista() == null || palabra.getPista().trim().isEmpty()) {
                palabra.setPalabra("ERROR_PISTA_VACIA");
                return palabra;
            }

            existingPalabra.setPalabra(palabra.getPalabra());
            existingPalabra.setPista(palabra.getPista());

            return palabraRepository.save(existingPalabra);
        }
        return null;
    }

    @Override
    public String deletePalabra(Integer id) {
        if (palabraRepository.existsById(id)) {
            palabraRepository.deleteById(id);
            return "Palabra eliminada exitosamente";
        } else {
            return "No se encontró la palabra con ID: " + id;
        }
    }
}
