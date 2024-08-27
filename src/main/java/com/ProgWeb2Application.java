package com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class ProgWeb2Application {

    public static void main(String[] args) {
       
    	
        SpringApplication.run(ProgWeb2Application.class, args);
    }

    @Bean
    public CommandLineRunner openBrowser() {
        return args -> {
            String url = "http://localhost:8080";
            String[] commands = {"cmd", "/c", "start", url};

            try {
                Runtime.getRuntime().exec(commands);
            } catch (IOException e) {
                System.err.println("Unable to open browser: " + e.getMessage());
            }
        };
    }
}
