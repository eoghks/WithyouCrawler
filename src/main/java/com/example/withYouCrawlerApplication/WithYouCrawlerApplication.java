package com.example.withYouCrawlerApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.withYouCrawlerApplication", "com.example"})
public class WithYouCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WithYouCrawlerApplication.class, args);
	}

}
