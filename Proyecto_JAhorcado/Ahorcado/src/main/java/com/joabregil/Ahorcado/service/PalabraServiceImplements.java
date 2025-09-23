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
        // Validation: word cannot be null, too short, or contain numbers
        if (palabra.getPalabra() == null || palabra.getPalabra().trim().length() < 3 || palabra.getPalabra().matches(".*\\d.*")) {
            Palabra resultado = new Palabra();
            resultado.setPalabra("ERROR_PALABRA_INVALIDA");
            return resultado;
        }

        // Validation: hint cannot be empty
        if (palabra.getPista() == null || palabra.getPista().trim().isEmpty()) {
            Palabra resultado = new Palabra();
            resultado.setPalabra("ERROR_PISTA_VACIA");
            return resultado;
        }

        // Validation: check for duplicates
        if (palabraRepository.findByPalabra(palabra.getPalabra()) != null) {
            Palabra resultado = new Palabra();
            resultado.setPalabra("ERROR_DUPLICADO");
            return resultado;
        }

        return palabraRepository.save(palabra);
    }

    @Override
    public Palabra updatePalabra(Integer id, Palabra palabra) {
        Palabra existingPalabra = palabraRepository.findById(id).orElse(null);
        if (existingPalabra != null) {
            // Validation: word cannot be null, too short, or contain numbers
            if (palabra.getPalabra() == null || palabra.getPalabra().trim().length() < 3 || palabra.getPalabra().matches(".*\\d.*")) {
                palabra.setPalabra("ERROR_PALABRA_INVALIDA");
                return palabra;
            }

            // Validation: hint cannot be empty
            if (palabra.getPista() == null || palabra.getPista().trim().isEmpty()) {
                palabra.setPalabra("ERROR_PISTA_VACIA");
                return palabra;
            }

            // Validation: check for duplicates when updating
            Palabra duplicatePalabra = palabraRepository.findByPalabra(palabra.getPalabra());
            if (duplicatePalabra != null && !duplicatePalabra.getCodigoPalabra().equals(id)) {
                palabra.setPalabra("ERROR_DUPLICADO");
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

    @Override
    public boolean existsById(Integer id) {
        return palabraRepository.existsById(id);
    }
}