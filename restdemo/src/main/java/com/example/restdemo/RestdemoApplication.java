package com.example.restdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@SpringBootApplication

@EnableJpaRepositories(exclude = {DataSourceAutoConfiguration.class})

public class RestdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestdemoApplication.class, args);
	}

}
