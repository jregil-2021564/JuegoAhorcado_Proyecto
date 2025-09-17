package com.joabregil.Ahorcado.service;
import com.joabregil.Ahorcado.model.Palabra;
import java.util.List;

public interface PalabraService {
    List<Palabra> getAllPalabras();
    Palabra getPalabraById(Integer id);
    Palabra savePalabra(Palabra palabra);
    Palabra updatePalabra(Integer id, Palabra palabra);
    String deletePalabra(Integer id);
}