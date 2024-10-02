package com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication // Annotation to mark this class as a Spring Boot application
public class ProgWeb2Application {

    public static void main(String[] args) {
        // The entry point of the Spring Boot application
        SpringApplication.run(ProgWeb2Application.class, args);
    }

    // Bean that runs a command line action after the application context is loaded
    @Bean
    public CommandLineRunner openBrowser() {
        return args -> {
            String url = "http://localhost:8080"; // The URL to open in the browser
            String[] commands = {"cmd", "/c", "start", url}; // Command to execute

            try {
                // Execute the command to open the browser
                Runtime.getRuntime().exec(commands);
            } catch (IOException e) {
                // Handle exceptions in case the browser fails to open
                System.err.println("Unable to open browser: " + e.getMessage());
            }
        }; 
    }
}
