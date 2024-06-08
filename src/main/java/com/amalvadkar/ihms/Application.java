package com.amalvadkar.ihms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import java.util.Random;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(ApplicationProperties.class)
public class Application {
	public static void main(String[] args) {
		String username = "admin";
		String password = "test";
		System.out.println(password);
		SpringApplication.run(Application.class, args);
	}

	public String random() {
        // Insecure random number generation
        Random random = new Random();
        return "Random number: " + random.nextInt();
	}
}
