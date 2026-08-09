package com.barbook.booking;

import com.barbook.booking.config.AdminProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AdminProperties.class)
public class BookingServiceApplication {

	public static void main(String[] args) {

		System.out.println("Hello World, Finally Running the project!");

		SpringApplication.run(BookingServiceApplication.class, args);


	}

}
