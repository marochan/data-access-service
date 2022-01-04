package com.filmlebendczil.dataaccessservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.filmlebendczil.dataccessservice.controller")
@ComponentScan("com.filmlebendczil.dataaccessservice.repository")
public class DataAccessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataAccessServiceApplication.class, args);
	}

	
	    
	    @RequestMapping("/greeting")
	     public String greetingString() {
	        return "Hello from the data service";
	    }
}
