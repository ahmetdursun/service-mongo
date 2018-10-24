package com.ibtech.microservices.servicemongo;

import com.ibtech.microservices.servicemongo.data.Car;
import com.ibtech.microservices.servicemongo.data.CarRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

@SpringBootApplication
public class ServiceMongoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMongoApplication.class, args);
	}
}
