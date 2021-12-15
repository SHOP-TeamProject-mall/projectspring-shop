package com.example.projectspringshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = { "com.example.controller", "com.example.service", "com.example.security",
		"com.example.jwt","com.example.impl"})
@EntityScan(basePackages = { "com.example.entity" })
@EnableJpaRepositories(basePackages = { "com.example.repository" })
public class ProjectspringShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectspringShopApplication.class, args);
		System.out.println("+++Starting Server+++");
	}

}
