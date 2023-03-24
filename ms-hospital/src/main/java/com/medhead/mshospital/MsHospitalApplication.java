package com.medhead.mshospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MsHospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsHospitalApplication.class, args);
	}

}
