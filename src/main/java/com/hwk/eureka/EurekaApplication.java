package com.hwk.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {

	public static void main(String[] args) {
		System.out.println( "Hello World ! EurekaApplication!" );
		SpringApplication.run(EurekaApplication.class, args);
	}

}
