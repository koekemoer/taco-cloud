package com.lk.authserver;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lk.authserver.users.User;
import com.lk.authserver.users.UserRepo;

@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@Bean
	public ApplicationRunner dataLoader(UserRepo userRepo, PasswordEncoder encoder) {
		return args -> {
			userRepo.save(new User("lk", encoder.encode("password"), "ROLE_ADMIN"));
		};
	}

}
