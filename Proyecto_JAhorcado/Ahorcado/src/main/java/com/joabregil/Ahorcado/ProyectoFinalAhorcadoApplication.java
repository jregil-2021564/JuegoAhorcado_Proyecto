package com.joabregil.Ahorcado;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoFinalAhorcadoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalAhorcadoApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hola Mundo");
    }
}
