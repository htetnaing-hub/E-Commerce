package com.glowmart.shop_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the GlowMart Shop Management application.
 * <p>
 * This class bootstraps the Spring Boot application by invoking
 * {@link SpringApplication#run(Class, String...)}. It enables auto-configuration,
 * component scanning, and configuration properties through the
 * {@link SpringBootApplication} annotation.
 * </p>
 */
@SpringBootApplication
public class ShopManagementApplication {

	/**
	 * Main method that launches the Spring Boot application.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(ShopManagementApplication.class, args);
	}

}
