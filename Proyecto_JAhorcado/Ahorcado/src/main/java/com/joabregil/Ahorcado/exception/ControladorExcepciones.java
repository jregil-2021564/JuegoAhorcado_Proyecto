package com.joabregil.Ahorcado.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControladorExcepciones {

    // Maneja la excepción cuando las credenciales de la base de datos son incorrectas.
    @ExceptionHandler({CannotGetJdbcConnectionException.class, MetaDataAccessException.class})
    public ResponseEntity<Map<String, Object>> manejarErrorConexionDB(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new java.util.Date());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Database Connection Error");
        body.put("message", "No se pudo conectar a la base de datos. Por favor, revise el usuario, contraseña o la URL en application.properties.");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Maneja la excepción cuando la URL no coincide con ningún endpoint.
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarRutaIncorrecta(NoHandlerFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new java.util.Date());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", "La ruta API que ha solicitado no existe. Por favor, verifique la URL. Ruta solicitada: " + ex.getRequestURL());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Maneja la excepción cuando el JSON de la solicitud está mal formado o faltan campos.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarFormatoInvalido(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new java.util.Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "El JSON de la solicitud está mal formado o los tipos de datos son incorrectos. Verifique el formato y que no falten columnas.");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Maneja la excepción cuando falta un @PathVariable en la URL.
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<Map<String, Object>> handleMissingPathVariable() {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new java.util.Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Falta un parámetro en la URL. Por favor, verifique la URL. Ejemplo: /api/usuarios/{id}");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Maneja la excepción cuando el tipo de dato de un parámetro de la URL es incorrecto.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> manejarErrorTipoParametro(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new java.util.Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "El tipo de dato del parámetro '" + ex.getName() + "' es incorrecto. Se esperaba un número.");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}