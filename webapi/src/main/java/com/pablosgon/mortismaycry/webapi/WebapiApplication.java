package com.pablosgon.mortismaycry.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebapiApplication {

	public static void main(String[] args) {
		try {
			Class.forName("org.modelmapper.ModelMapper");
			System.out.println("ModelMapper está accesible");
		} catch (ClassNotFoundException e) {
			System.out.println("ModelMapper NO está accesible");
		}
		SpringApplication.run(WebapiApplication.class, args);
	}

}
