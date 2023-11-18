package com.example.logengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class LogEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogEngineApplication.class, args);
	}

}
