package com.lk.tacocloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.lk.tacocloud.domain.Ingredient;
import com.lk.tacocloud.domain.User;
import com.lk.tacocloud.domain.Ingredient.Type;
import com.lk.tacocloud.repository.IngredientRepository;
import com.lk.tacocloud.repository.UserRepository;
import com.lk.tacocloud.security.RegistrationForm;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

}
