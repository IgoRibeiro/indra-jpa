package com.combustivel.distribuidora.distribuidora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan           (basePackages = {"com.combustivel.distribuidora.entity"})
@EnableJpaRepositories(basePackages = {"com.combustivel.distribuidora.repository"})
@ComponentScan        (basePackages = {"com.combustivel.distribuidora.controller.rest", 
									   "com.combustivel.distribuidora.service",
									   "com.combustivel.distribuidora.controller.csv",
									   "com.combustivel.distribuidora.distribuidora"})
public class DistribuidoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistribuidoraApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
			}
		};
	}	
	
}

