package com.joabregil.Ahorcado;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Importaciones del servidor
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.io.InputStream;
import java.util.Date;
import java.io.*;

@SpringBootApplication
public class ProyectoFinalAhorcadoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ProyectoFinalAhorcadoApplication.class, args);
        } catch (Exception e) {
            if (esPuertoOcupado(e)) {
                int puertoPrincipal = 8080; // Puerto por defecto
                int puertoReserva = puertoPrincipal + 1;
                System.out.println("Puerto " + puertoPrincipal + " no disponible. Iniciando servidor alterno en puerto " + puertoReserva );
                iniciarServidorAlterno(puertoReserva, puertoPrincipal);
            } else {
                e.printStackTrace();
            }
        }
    }

    private static boolean esPuertoOcupado(Exception e) {
        Throwable causa = e;
        while (causa != null) {
            if (causa instanceof BindException) return true;
            causa = causa.getCause();
        }
        return false;
    }

    private static void iniciarServidorAlterno(int puertoReserva, int puertoOriginal) {
        try {
            HttpServer servidor = HttpServer.create(new InetSocketAddress(puertoReserva), 0);
            String mensajeError = "{\"aviso\": \"Servicio no disponible - Puerto " + puertoOriginal + " en uso, Por Favor Cambia de Puerto\", \"fecha\": \"" + new Date() + "\"}";
            byte[] respuesta = mensajeError.getBytes("UTF-8");

            // Configurar rutas principales
            String[] rutas = {"/api/usuarios", "/api/palabras"};
            for (String ruta : rutas) {
                servidor.createContext(ruta, exchange -> {
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(503, respuesta.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(respuesta);
                    os.close();
                });
            }

            servidor.start();
            System.out.println("Servidor de aviso activo: http://localhost:" + puertoReserva + "/api/usuarios");

        } catch (IOException ex) {
            System.out.println("Error al iniciar servidor alterno: " + ex.getMessage());
        }
    }

    @Override
    public void run(String... args) {
        System.out.println("Aplicación lista para usar");
    }
}