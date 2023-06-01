package com.lk.tacocloud.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.lk.tacocloud.domain.Ingredient;
import com.lk.tacocloud.domain.Ingredient.Type;
import com.lk.tacocloud.repository.IngredientRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepository;
    
    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    // public IngredientByIdConverter() {
    //     ingredientMap.put("FLTO", new Ingredient("FLTO", "Flower Tortilla", Type.WRAP));
    //     ingredientMap.put("COTO", new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
    //     ingredientMap.put("GRBF", new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
    //     ingredientMap.put("CARN", new Ingredient("CARN", "Carnitas", Type.PROTEIN));
    //     ingredientMap.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
    //     ingredientMap.put("LETC", new Ingredient("LETC", "Lettuce", Type.VEGGIES));
    //     ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Type.CHEESE));
    //     ingredientMap.put("JACK", new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
    //     ingredientMap.put("SLSA", new Ingredient("SLSA", "Salsa", Type.SAUCE));
    //     ingredientMap.put("SRCR", new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
    // }

    @Override
    public Ingredient convert(String id) {
        return this.ingredientRepository.findById(id).orElse(null);
    }

    

}
