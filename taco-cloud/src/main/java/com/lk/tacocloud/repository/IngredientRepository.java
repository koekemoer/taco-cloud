package com.lk.tacocloud.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lk.tacocloud.domain.Ingredient;

// @Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> { // cause ingredient ID is string
    
}
