package com.minsait.equipo2.msvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients
public class MsvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcApplication.class, args);
	}
}