package com.lk.tacocloud.bootstrap;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lk.tacocloud.domain.Ingredient;
import com.lk.tacocloud.domain.User;
import com.lk.tacocloud.domain.Ingredient.Type;
import com.lk.tacocloud.repository.IngredientRepository;
import com.lk.tacocloud.repository.UserRepository;

@Configuration
public class DevConfig {
    
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                ingredientRepository.deleteAll();
                userRepository.deleteAll();

                userRepository.save(new User("q", encoder.encode("q"), "Flou Lou", "123 Flaco Ave", "Townsville", "AB", "1234", "0123456789"));

                ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
                ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
                ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
                ingredientRepository.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
                ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
                ingredientRepository.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
                ingredientRepository.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
                ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
                ingredientRepository.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
                ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
            }
            

        };
    }
}
