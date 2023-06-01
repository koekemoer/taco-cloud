package com.lk.tacocloud.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Date placedAt = new Date();

    @ManyToOne
    private User user;

    @NotBlank(message="Delivery Name is Required")
    private String deliveryName;

    @NotBlank(message="Street Address is Required")
    private String deliveryStreet;

    @NotBlank(message="City is Required")
    private String deliveryCity;

    @NotBlank(message="State is Required")
    private String deliveryState;

    @NotBlank(message="Zip Code is Required")
    private String deliveryZip;
    
    // Luhn Algorithm for Credit Card Validation - https://creditcardvalidator.org/articles/luhn-algorithm
    @CreditCardNumber(message="Not a valid Credit Card Number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @OneToMany(targetEntity = Taco.class) // Cascade - if this TacoOrder is deleted, all tacos associated will also be deleted
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
    
}
