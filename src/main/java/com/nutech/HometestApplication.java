package com.nutech;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class HometestApplication {

	@Value("${security.secret-key}")
    private String secretKey;

	public static void main(String[] args) {
		SpringApplication.run(HometestApplication.class, args);
	}

	@PostConstruct
	public void init(){
		log.info("Start Home Test Application : {}", secretKey);
	}

	@PreDestroy
	public void destroy(){
		log.info("Application Stop");
	}

	

}
