package com.provet.receta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RecetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecetaApplication.class, args);
	}

}
