package com.joabregil.Ahorcado.Controller;

import com.joabregil.Ahorcado.model.Palabra;
import com.joabregil.Ahorcado.service.PalabraService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
        // Validate against missing or malformed fields
        if (palabra.getPalabra() == null) {
            return "Error: Falta la columna 'palabra'.";
        }
        if (palabra.getPista() == null) {
            return "Error: Falta la columna 'pista'.";
        }

        Palabra result = palabraService.savePalabra(palabra);

        if ("ERROR_PALABRA_INVALIDA".equals(result.getPalabra())) {
            return "Error: La palabra debe tener al menos 3 caracteres y solo letras.";
        }
        if ("ERROR_PISTA_VACIA".equals(result.getPalabra())) {
            return "Error: La pista no puede estar vacía.";
        }
        if ("ERROR_DUPLICADO".equals(result.getPalabra())) {
            return "Error: La palabra ya fue registrada previamente.";
        }

        return "Palabra agregada exitosamente.";
    }

    @PutMapping("/{id}")
    public String updatePalabra(@PathVariable Integer id, @RequestBody Palabra palabra) {
        // Validate against missing or malformed fields
        if (palabra.getPalabra() == null) {
            return "Error: Falta la columna 'palabra'.";
        }
        if (palabra.getPista() == null) {
            return "Error: Falta la columna 'pista'.";
        }

        Palabra result = palabraService.updatePalabra(id, palabra);

        if (result == null) {
            return "No se encontró la palabra con ID: " + id + ".";
        }

        if ("ERROR_PALABRA_INVALIDA".equals(result.getPalabra())) {
            return "Error: La palabra debe tener al menos 3 caracteres y solo letras.";
        }
        if ("ERROR_PISTA_VACIA".equals(result.getPalabra())) {
            return "Error: La pista no puede estar vacía.";
        }
        if ("ERROR_DUPLICADO".equals(result.getPalabra())) {
            return "Error: La palabra ya fue registrada previamente.";
        }

        return "Actualización exitosa.";
    }

    @DeleteMapping("/{id}")
    public String deletePalabra(@PathVariable Integer id) {
        if (!palabraService.existsById(id)) {
            return "No se encontró la palabra con ese ID para ser eliminada.";
        }
        palabraService.deletePalabra(id);
        return "Palabra eliminada correctamente.";
    }

    // This handles missing @PathVariable which would result in a 404
    @ExceptionHandler(org.springframework.web.bind.MissingPathVariableException.class)
    public ResponseEntity<Map<String, Object>> handleMissingPathVariable() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Falta el parámetro de la URL. Por favor, verifique la URL.");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}