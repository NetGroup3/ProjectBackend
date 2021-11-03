package com.example.NetProjectBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NetProjectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetProjectBackendApplication.class, args);

		System.out.println("Hello world");
		System.out.println("press ctr+d for copy this line down");

		System.out.println("press ctr+y for remove this line");
	}
}
