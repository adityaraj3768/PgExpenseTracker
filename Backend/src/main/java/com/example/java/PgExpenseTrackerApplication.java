package com.example.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PgExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PgExpenseTrackerApplication.class, args);
		// System.out.println("JDBC URL: " + System.getenv("DATASOURCE_URL"));


		System.out.println("Expense Tracker Application is running!");
	}

}
