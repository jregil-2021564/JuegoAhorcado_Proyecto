package com.joabregil.Ahorcado.Controller;

import com.joabregil.Ahorcado.model.Palabra;
import com.joabregil.Ahorcado.service.PalabraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/palabras")
public class PalabraController {

    private final PalabraService palabraService;

    public PalabraController(PalabraService palabraService) {
        this.palabraService = palabraService;
    }

    @GetMapping
    public List<Palabra> getAllPalabras() {
        return palabraService.getAllPalabras();
    }

    @GetMapping("/{id}")
    public Palabra getPalabraById(@PathVariable Integer id) {
        return palabraService.getPalabraById(id);
    }

    @PostMapping
    public String createPalabra(@RequestBody Palabra palabra) {
        Palabra result = palabraService.savePalabra(palabra);

        if ("ERROR_PALABRA_INVALIDA".equals(result.getPalabra())) {
            return "Error: La palabra debe tener al menos 3 caracteres válidos";
        }
        if ("ERROR_PISTA_VACIA".equals(result.getPalabra())) {
            return "Error: La pista no puede estar vacía";
        }

        return "Palabra agregada exitosamente";
    }

    @PutMapping("/{id}")
    public String updatePalabra(@PathVariable Integer id, @RequestBody Palabra palabra) {
        Palabra result = palabraService.updatePalabra(id, palabra);

        if (result == null) {
            return "No se encontró la palabra con ID: " + id;
        }
        if ("ERROR_PALABRA_INVALIDA".equals(result.getPalabra())) {
            return "Error: La palabra debe tener al menos 3 caracteres válidos";
        }
        if ("ERROR_PISTA_VACIA".equals(result.getPalabra())) {
            return "Error: La pista no puede estar vacía";
        }

        return "Actualización exitosa";
    }

    @DeleteMapping("/{id}")
    public String deletePalabra(@PathVariable Integer id) {
        return palabraService.deletePalabra(id);
    }
}
