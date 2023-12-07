package main.java.coms309;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sample Spring Boot Application.
 * 
 * @author Vivek Bengre, Arie Kraayenbrink
 */

@SpringBootApplication
public class Application {
	
    
    /** 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            SpringApplication.run(Application.class, args);            
        } catch (Exception e) {
            System.out.println("Error happened. Here is the message: " + e.getMessage());
            // TODO: handle exception
        }
    }

}
