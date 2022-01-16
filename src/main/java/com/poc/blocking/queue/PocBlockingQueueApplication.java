package com.poc.blocking.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PocBlockingQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocBlockingQueueApplication.class, args);
	}

}
