package com.huaguoguo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;

@SpringBootApplication
@EnableCaching
public class HellohuaguoguoApplication {



	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		SpringApplication.run(HellohuaguoguoApplication.class, args);
	}
}
