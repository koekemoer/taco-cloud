package com.lk.tacocloud.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.lk.tacocloud.domain.User;

import lombok.Data;

@Data
public class RegistrationForm {
    
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder encoder) {
        // return new User(username, encoder.encode(password), fullname, street, city, state, zip, phone);
        return new User(username, encoder.encode(password));
    }

}
