package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping( "/test")
	public Mono<String> test() {
		return Mono.just("test");
	}

	@GetMapping( "/error")
	public Mono<String> error() {
		if(true)
			throw new RuntimeException("test exception");
		return Mono.just("test");
	}

}
