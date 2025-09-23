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

@SpringBootApplication
public class ProyectoFinalAhorcadoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        try {
            // Intenta iniciar la aplicación principal de Spring Boot
            SpringApplication.run(ProyectoFinalAhorcadoApplication.class, args);
        } catch (Exception excepcionInicio) {
            Throwable causaRaiz = excepcionInicio;
            boolean puertoOcupado = false;

            // Revisa la cadena de excepciones en busca de un BindException
            while (causaRaiz != null) {
                if (causaRaiz instanceof BindException) {
                    puertoOcupado = true;
                    break;
                }
                causaRaiz = causaRaiz.getCause();
            }

            if (puertoOcupado) {
                int puertoPrincipal = obtenerPuertoDePropiedades();
                int puertoReserva = puertoPrincipal + 1;
                System.err.println("¡ERROR CRÍTICO! El puerto " + puertoPrincipal + " ya está en uso. Levantando un servidor de reserva en el puerto " + puertoReserva);

                try {
                    iniciarServidorReserva(puertoReserva, puertoPrincipal);
                } catch (IOException excepcionIO) {
                    excepcionIO.printStackTrace();
                }
            } else {
                excepcionInicio.printStackTrace();
            }
        }
    }

    private static int obtenerPuertoDePropiedades() {
        Properties propiedades = new Properties();
        try (InputStream flujoEntrada = ProyectoFinalAhorcadoApplication.class
                .getClassLoader().getResourceAsStream("application.properties")) {
            if (flujoEntrada != null) {
                propiedades.load(flujoEntrada);
                return Integer.parseInt(propiedades.getProperty("server.port", "8080"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 8080;
    }
    private static void iniciarServidorReserva(int puertoReserva, int puertoOriginal) throws IOException {
        HttpServer servidorAdvertencia = HttpServer.create(new InetSocketAddress(puertoReserva), 0);
        String jsonError = "{\"error\": \"El puerto " + puertoOriginal + " está ocupado. La API no pudo iniciar. Por favor, cambia el puerto.\", \"timestamp\": \"" + new Date() + "\"}";
        byte[] contenidoRespuesta = jsonError.getBytes("UTF-8");

        servidorAdvertencia.createContext("/api/usuarios", exchange -> {
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(503, contenidoRespuesta.length); // 503 Servicio No Disponible
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(contenidoRespuesta);
            }
        });
        servidorAdvertencia.createContext("/api/palabras", exchange -> {
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(503, contenidoRespuesta.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(contenidoRespuesta);
            }
        });
        servidorAdvertencia.start();

        System.out.println("Se ha activado un servidor de advertencia. Ve el mensaje en http://localhost:" + puertoReserva + "/api/usuarios");
    }

    @Override
    public void run(String... args) {
        System.out.println("La API está funcionando. ¡Bienvenido!");
    }
}