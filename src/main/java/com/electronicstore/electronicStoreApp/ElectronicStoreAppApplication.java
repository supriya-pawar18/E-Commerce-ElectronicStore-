package com.electronicstore.electronicStoreApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElectronicStoreAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreAppApplication.class, args);
		System.out.println("started");
	}

	@Bean
	public org.modelmapper.ModelMapper modelMapper() {
		return new org.modelmapper.ModelMapper();
	}

}
