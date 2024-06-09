package com.amalvadkar.ihms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import java.sql.*;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(ApplicationProperties.class)
public class Application {
	public static void main(String[] args) {
	     SpringApplication.run(Application.class, args);
	}

	


    public static void testCodeQL() {
        // Vulnerable code: concatenating user input into SQL query
        String username = "admin";
        String password = "test";
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "password");
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
            ResultSet rs = stmt.executeQuery(query); // Vulnerable to SQL injection
            
            // Process result set
            while (rs.next()) {
                // Do something with the results
            }
            
            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
