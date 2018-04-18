package com.huaguoguo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HellohuaguoguoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellohuaguoguoApplication.class, args);
	}
}
