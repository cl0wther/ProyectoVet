package com.provet.favoritos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class FavoritosApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavoritosApplication.class, args);
	}

}
