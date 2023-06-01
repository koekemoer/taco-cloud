package com.lk.tacocloud.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lk.tacocloud.domain.Ingredient;
import com.lk.tacocloud.domain.Taco;
import com.lk.tacocloud.domain.TacoOrder;
import com.lk.tacocloud.domain.User;
import com.lk.tacocloud.domain.Ingredient.Type;
import com.lk.tacocloud.repository.IngredientRepository;
import com.lk.tacocloud.repository.TacoRepository;
import com.lk.tacocloud.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * DesignTacoController is annotated with @SessionAttributes
    ("tacoOrder"). This indicates that the TacoOrder object that is put into the model a
    little later in the class should be maintained in session. This is important because the
    Displaying information 
    creation of a taco is also the first step in creating an order, and the order we create will
    need to be carried in the session so that it can span multiple requests.
 */

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private UserRepository userRepository;
    private TacoRepository tacoRepository;
    // private TacoRepo
    
    public DesignTacoController(IngredientRepository ingredientRepository, UserRepository userRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        log.info("IN ADD_INGREDIENT");
        List<Ingredient> ingredients = new ArrayList<>();

        this.ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), this.filterByType(ingredients, type));
        }        
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        // x == ingredients
        // return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
        return ingredients.stream()
                    .filter(i -> i.getType().equals(type))
                    .collect(Collectors.toList());
    }

    @ModelAttribute(name = "order")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal) {
            String username = principal.getName();
            log.info("USERNAME = " + username);
            User user = userRepository.findByUsername(username);
            return user;
    }

    @GetMapping
    public String showDesignForm() {
        log.info("SHOWING DESIGN FORM");
        return "design"; // taco design
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute("order") TacoOrder order) {
        log.info("PROCESSING TACO");
        if (errors.hasErrors()) {
            log.info("Process Taco has no errors");
            return "design";
        }

        log.info("Saving taco: " + taco);
        Taco saved = tacoRepository.save(taco);
        order.addTaco(saved);
        log.info("ORDER SIZE AFTER SAVING = " + order.getTacos().size());
        log.info("TacoOrder -> {}", order);

        log.info("Adding taco {}", taco);

        return "redirect:/orders/current";
    }
}
